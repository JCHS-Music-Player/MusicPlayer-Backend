package com.github.ioloolo.mongo_jwt_security.domain.auth.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class LoginRequest {

	@NotBlank
	private final String username;

	@NotBlank
	private final String password;
}
