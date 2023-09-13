package com.github.ioloolo.music_player.common.spotify;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;

@Slf4j
@Component
public class Spotify {

	@Value("${app.spotify.clientId}")
	private String clientId;

	@Value("${app.spotify.clientSecret}")
	private String clientSecret;

	@Bean
	@SneakyThrows
	public SpotifyApi spotifyApi() {
		SpotifyApi api = SpotifyApi.builder()
				.setClientId(clientId)
				.setClientSecret(clientSecret)
				.build();

		ClientCredentials credentials = api.clientCredentials().build().execute();

		api.setAccessToken(credentials.getAccessToken());

		return api;
	}
}
