package com.github.ioloolo.music_player.domain.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ioloolo.music_player.domain.auth.service.AuthService;
import com.github.ioloolo.music_player.domain.auth.exception.AlreadyTakenUsernameException;
import com.github.ioloolo.music_player.domain.auth.payload.request.LoginRequest;
import com.github.ioloolo.music_player.domain.auth.payload.request.RegisterRequest;
import com.github.ioloolo.music_player.domain.auth.payload.response.JwtResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService service;

	@PostMapping
	public ResponseEntity<JwtResponse> login(@Validated @RequestBody LoginRequest loginRequest) {
		String username = loginRequest.getUsername();
		String password = loginRequest.getPassword();

		String jwtToken = service.login(username, password);

		return ResponseEntity.ok(new JwtResponse(jwtToken));
	}

	@PutMapping
	public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest signUpRequest) throws AlreadyTakenUsernameException {
		String username = signUpRequest.getUsername();
		String password = signUpRequest.getPassword();

		service.register(username, password);

		return ResponseEntity.ok(new JwtResponse(service.login(username, password)));
	}
}
