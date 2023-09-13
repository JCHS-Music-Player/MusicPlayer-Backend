package com.github.ioloolo.music_player.domain.playlist.service;

import java.io.IOException;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import com.github.ioloolo.music_player.domain.auth.model.User;
import com.github.ioloolo.music_player.domain.auth.repository.UserRepository;
import com.github.ioloolo.music_player.domain.playlist.model.Playlist;
import com.github.ioloolo.music_player.domain.playlist.exception.AlreadyExistsPlaylistNameException;
import com.github.ioloolo.music_player.domain.playlist.repository.PlaylistRepository;
import com.github.ioloolo.music_player.domain.track.data.SpotifyTrack;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Service
@RequiredArgsConstructor
public class PlaylistService {

	private final SpotifyApi api;

	private final UserRepository userRepository;
	private final PlaylistRepository playlistRepository;

	public Playlist createPlaylist(String userId, String name) throws AlreadyExistsPlaylistNameException {
		User user = userRepository.findById(userId).orElseThrow();

		if (playlistRepository.existsByUserAndName(user, name)) {
			throw new AlreadyExistsPlaylistNameException();
		}

		return playlistRepository.save(
				Playlist.builder()
						.name(name)
						.tracks(List.of())
						.user(user)
						.build()
		);
	}

	public List<SpotifyTrack> getTrack(String playlistId) {
		Playlist playlist = playlistRepository.findById(playlistId).orElseThrow();

		return playlist.getTracks();
	}

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public void addTrack(String userId, String playlistId, String trackId) {
		User user = userRepository.findById(userId).orElseThrow();
		Playlist playlist = playlistRepository.findByUserAndId(user, playlistId).orElseThrow();

		Track track = api.getTrack(trackId).build().execute();

		if (playlist.getTracks().contains(SpotifyTrack.from(track))) {
			return;
		}

		playlist.getTracks().add(SpotifyTrack.from(track));

		playlistRepository.save(playlist);
	}

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public void removeTrack(String userId, String playlistId, String trackId) {
		User user = userRepository.findById(userId).orElseThrow();
		Playlist playlist = playlistRepository.findByUserAndId(user, playlistId).orElseThrow();

		Track track = api.getTrack(trackId).build().execute();

		if (!playlist.getTracks().contains(SpotifyTrack.from(track))) {
			return;
		}

		playlist.getTracks().remove(SpotifyTrack.from(track));

		playlistRepository.save(playlist);
	}
}
