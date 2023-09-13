package com.github.ioloolo.music_player.domain.track.controller;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.music_player.common.config.security.services.UserDetailsImpl;
import com.github.ioloolo.music_player.domain.history.service.HistoryService;
import com.github.ioloolo.music_player.domain.track.payload.request.PlayTrackRequest;
import com.github.ioloolo.music_player.domain.track.payload.request.TrackSearchRequest;
import com.github.ioloolo.music_player.domain.track.service.TrackService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/track")
public class TrackController {

	private final TrackService service;
	private final HistoryService historyService;

	@PostMapping("/search")
	public ResponseEntity<?> searchTrack(@Validated @RequestBody TrackSearchRequest request) {
		return ResponseEntity.ok(service.searchTrack(request.getQuery()));
	}

	@PostMapping("/play")
	@SneakyThrows({FileNotFoundException.class, IOException.class})
	public ResponseEntity<InputStreamResource> playTrack(Authentication authentication, @Validated @RequestBody PlayTrackRequest request) {
		service.downloadTrack(request.getTrack());

		historyService.addTrackToHistory(((UserDetailsImpl) authentication.getPrincipal()).getId(), request.getTrack());

		String mp3FileName = "%s.mp3".formatted(request.getTrack());
		String filePath = "./music/%s".formatted(mp3FileName);

		InputStream mp3FileStream = new FileInputStream(filePath);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("audio/mpeg"));

		headers.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(mp3FileStream.available()));
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + mp3FileName);

		return ResponseEntity.ok()
				.headers(headers)
				.body(new InputStreamResource(mp3FileStream));
	}

	@PostMapping("/recommend")
	public ResponseEntity<?> recommend(Authentication authentication) {
		return ResponseEntity.ok(service.recommend(((UserDetailsImpl) authentication.getPrincipal()).getId()));
	}
}
