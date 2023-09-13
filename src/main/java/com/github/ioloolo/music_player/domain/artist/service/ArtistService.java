package com.github.ioloolo.music_player.domain.artist.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Service;

import com.github.ioloolo.music_player.domain.album.data.SpotifyAlbum;
import com.github.ioloolo.music_player.domain.artist.data.SpotifyArtist;
import com.github.ioloolo.music_player.domain.track.data.SpotifyTrack;
import com.neovisionaries.i18n.CountryCode;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;

@Service
@RequiredArgsConstructor
public class ArtistService {

	private final SpotifyApi api;

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public List<SpotifyArtist> searchArtist(String query) {
		Paging<Artist> execute = api.searchArtists(query)
				.market(CountryCode.KR)
				.limit(50)
				.build()
				.execute();

		Artist[] artists = execute.getItems();

		return Arrays.stream(artists)
				.map(SpotifyArtist::from)
				.toList();
	}

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public List<SpotifyTrack> getTopTrack(String id) {
		Track[] tracks = api.getArtistsTopTracks(id, CountryCode.KR)
				.build()
				.execute();

		System.out.println(Arrays.toString(tracks));

		return Arrays.stream(tracks)
				.map(SpotifyTrack::from)
				.toList();
	}

	@SneakyThrows({IOException.class, ParseException.class, SpotifyWebApiException.class})
	public List<SpotifyAlbum> getAlbums(String id) {
		Paging<AlbumSimplified> execute = api.getArtistsAlbums(id)
				.market(CountryCode.KR)
				.limit(50)
				.build()
				.execute();

		AlbumSimplified[] items = execute.getItems();

		return Arrays.stream(items)
				.map(SpotifyAlbum::from)
				.toList();
	}
}
