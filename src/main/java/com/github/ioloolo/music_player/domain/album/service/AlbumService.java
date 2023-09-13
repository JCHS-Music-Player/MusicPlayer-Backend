package com.github.ioloolo.music_player.domain.album.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import com.github.ioloolo.music_player.domain.album.data.SpotifyAlbum;
import com.github.ioloolo.music_player.domain.track.data.SpotifyTrack;
import com.neovisionaries.i18n.CountryCode;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Service
@RequiredArgsConstructor
public class AlbumService {

	private final SpotifyApi api;

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public List<SpotifyAlbum> searchAlbum(String query) {
		Paging<AlbumSimplified> execute = api.searchAlbums(query)
				.market(CountryCode.KR)
				.limit(50)
				.build()
				.execute();

		AlbumSimplified[] albums = execute.getItems();

		return Arrays.stream(albums)
				.map(SpotifyAlbum::from)
				.toList();
	}

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public List<SpotifyTrack> getTracks(String id) {
		Paging<TrackSimplified> execute = api.getAlbumsTracks(id)
				.market(CountryCode.KR)
				.limit(50)
				.build()
				.execute();

		TrackSimplified[] items = execute.getItems();

		return Arrays.stream(items)
				.map(SpotifyTrack::from)
				.toList();
	}
}
