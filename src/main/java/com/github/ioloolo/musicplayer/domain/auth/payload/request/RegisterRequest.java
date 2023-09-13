package com.github.ioloolo.musicplayer.domain.auth.payload.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(min = 6, max = 24)
    private String username;

    @NotBlank
    @Size(min = 8, max = 32)
    private String password;
}
