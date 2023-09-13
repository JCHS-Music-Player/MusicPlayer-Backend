package com.github.ioloolo.music_player.domain.artist.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.music_player.domain.artist.payload.request.ArtistAlbumsRequest;
import com.github.ioloolo.music_player.domain.artist.payload.request.ArtistSearchRequest;
import com.github.ioloolo.music_player.domain.artist.payload.request.ArtistTopTrackRequest;
import com.github.ioloolo.music_player.domain.artist.service.ArtistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artist")
public class ArtistController {

	private final ArtistService service;

	@PostMapping("/search")
	public ResponseEntity<?> searchArtist(@Validated @RequestBody ArtistSearchRequest request) {
		return ResponseEntity.ok(service.searchArtist(request.getQuery()));
	}

	@PostMapping("/top")
	public ResponseEntity<?> getTopTrack(@Validated @RequestBody ArtistTopTrackRequest request) {
		return ResponseEntity.ok(service.getTopTrack(request.getId()));
	}

	@PostMapping("/albums")
	public ResponseEntity<?> getAlbums(@Validated @RequestBody ArtistAlbumsRequest request) {
		return ResponseEntity.ok(service.getAlbums(request.getId()));
	}
}
