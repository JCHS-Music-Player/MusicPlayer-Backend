package com.github.ioloolo.music_player.domain.history.service;

import java.io.IOException;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import com.github.ioloolo.music_player.domain.auth.model.User;
import com.github.ioloolo.music_player.domain.auth.repository.UserRepository;
import com.github.ioloolo.music_player.domain.history.model.History;
import com.github.ioloolo.music_player.domain.history.repository.HistoryRepository;
import com.github.ioloolo.music_player.domain.track.data.SpotifyTrack;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;

@Service
@RequiredArgsConstructor
public class HistoryService {

	private final SpotifyApi api;

	private final UserRepository userRepository;
	private final HistoryRepository historyRepository;

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public void addTrackToHistory(String userId, String trackId) {
		User user = userRepository.findById(userId).orElseThrow();
		History history = historyRepository.findByUser(user);

		history.getTracks().add(SpotifyTrack.from(api.getTrack(trackId).build().execute()));

		historyRepository.save(history);
	}
}
