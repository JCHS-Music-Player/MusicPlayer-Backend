package com.github.ioloolo.music_player.domain.playlist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.music_player.common.config.security.services.UserDetailsImpl;
import com.github.ioloolo.music_player.domain.playlist.exception.AlreadyExistsPlaylistNameException;
import com.github.ioloolo.music_player.domain.playlist.payload.request.AddTrackRequest;
import com.github.ioloolo.music_player.domain.playlist.payload.request.CreatePlaylistRequest;
import com.github.ioloolo.music_player.domain.playlist.payload.request.GetPlaylistTrackRequest;
import com.github.ioloolo.music_player.domain.playlist.payload.request.RemoveTrackRequest;
import com.github.ioloolo.music_player.domain.playlist.service.PlaylistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlist")
public class PlaylistController {

	private final PlaylistService service;

	@PutMapping()
	public ResponseEntity<?> searchArtist(Authentication authentication, @Validated @RequestBody CreatePlaylistRequest request) throws
			AlreadyExistsPlaylistNameException {
		String userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

		return ResponseEntity.ok(service.createPlaylist(userId, request.getName()));
	}

	@PostMapping("/track")
	public ResponseEntity<?> getTrack(@Validated @RequestBody GetPlaylistTrackRequest request) {
		return ResponseEntity.ok(service.getTrack(request.getPlaylist()));
	}

	@PutMapping("/track")
	public ResponseEntity<?> addTrack(Authentication authentication, @Validated @RequestBody AddTrackRequest request) {
		String userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

		service.addTrack(userId, request.getPlaylist(), request.getTrack());

		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/track")
	public ResponseEntity<?> removeTrack(Authentication authentication, @Validated @RequestBody RemoveTrackRequest request) {
		String userId = ((UserDetailsImpl)authentication.getPrincipal()).getId();

		service.removeTrack(userId, request.getPlaylist(), request.getTrack());

		return ResponseEntity.ok().build();
	}
}
