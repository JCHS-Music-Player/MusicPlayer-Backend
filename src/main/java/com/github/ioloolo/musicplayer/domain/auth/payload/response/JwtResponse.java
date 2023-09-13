package com.github.ioloolo.musicplayer.domain.auth.payload.response;

import lombok.Getter;

@Getter
public class JwtResponse {
	private final String token;

	public JwtResponse(String accessToken) {
		this.token = "Bearer " + accessToken;
	}
}
