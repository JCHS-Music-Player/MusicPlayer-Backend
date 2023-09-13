package com.github.ioloolo.music_player.domain.album.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.music_player.domain.album.payload.request.AlbumSearchRequest;
import com.github.ioloolo.music_player.domain.album.payload.request.GetAlbumTracksRequest;
import com.github.ioloolo.music_player.domain.album.service.AlbumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/album")
public class AlbumController {

	private final AlbumService service;

	@PostMapping("/search")
	public ResponseEntity<?> searchAlbum(@Validated @RequestBody AlbumSearchRequest request) {
		return ResponseEntity.ok(service.searchAlbum(request.getQuery()));
	}

	@PostMapping("/tracks")
	public ResponseEntity<?> getTracks(@Validated @RequestBody GetAlbumTracksRequest request) {
		return ResponseEntity.ok(service.getTracks(request.getId()));
	}
}
