package com.github.ioloolo.music_player.domain.track.service;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.github.ioloolo.music_player.domain.auth.model.User;
import com.github.ioloolo.music_player.domain.auth.repository.UserRepository;
import com.github.ioloolo.music_player.domain.history.repository.HistoryRepository;
import com.github.ioloolo.music_player.domain.track.data.SpotifyTrack;
import com.neovisionaries.i18n.CountryCode;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Service
@RequiredArgsConstructor
public class TrackService {

	@Value("${app.youtube.api.key}")
	private String youtubeApiKey;

	private final SpotifyApi api;

	private final UserRepository userRepository;
	private final HistoryRepository historyRepository;

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public List<SpotifyTrack> searchTrack(String query) {
		Paging<Track> execute = api.searchTracks(query)
				.market(CountryCode.KR)
				.limit(50)
				.build()
				.execute();

		Track[] tracks = execute.getItems();

		return Arrays.stream(tracks)
				.map(SpotifyTrack::from)
				.toList();
	}

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class, InterruptedException.class})
	public void downloadTrack(String trackId) {
		if (!Paths.get("./music/%s.mp3".formatted(trackId)).toFile().exists()) {
			SpotifyTrack track = SpotifyTrack.from(api.getTrack(trackId).build().execute());

			RestTemplate restTemplate = new RestTemplate();

			String query = track.getName() + " " + track.getAlbum().getName();
			String url = "https://www.googleapis.com/youtube/v3/search?key=%s&type=video&maxResults=1&q=%s".formatted(youtubeApiKey, query);

			Map<?, ?> response = restTemplate.getForObject(url, Map.class);

			if (response == null || response.get("items") == null || ((List<?>)response.get("items")).isEmpty()) {
				throw new RuntimeException("Music not found");
			}

			String videoId = (String) ((Map<?, ?>)((Map<?, ?>)((List<?>)response.get("items")).get(0)).get("id")).get("videoId");

			new ProcessBuilder()
					.command("sh", "-c", "cd %s && yt-dlp %s -x --audio-format mp3 -o %s".formatted(Paths.get("./music").toAbsolutePath().toString(), videoId, trackId))
					.start()
					.waitFor();
		}
	}

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public List<SpotifyTrack> recommend(String userId) {
		User user = userRepository.findById(userId).orElseThrow();

		String[] history = historyRepository.findByUser(user)
				.getTracks()
				.stream()
				.limit(50)
				.map(SpotifyTrack::getId)
				.toArray(String[]::new);

		Track[] tracks = api.getSeveralTracks(history)
				.build()
				.execute();

		return Arrays.stream(tracks).map(SpotifyTrack::from).toList();
	}

	@PostConstruct
	public void init() {
		Paths.get("./music").toFile().mkdirs();
	}
}
