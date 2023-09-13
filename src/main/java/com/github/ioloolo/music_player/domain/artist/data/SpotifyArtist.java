package com.github.ioloolo.music_player.domain.artist.data;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Artist;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;

@Data
public class SpotifyArtist {

	private String name;
	private String id;
	private String image;

	public static SpotifyArtist from(Artist artist) {
		SpotifyArtist spotifyArtist = new SpotifyArtist();

		spotifyArtist.setName(artist.getName());
		spotifyArtist.setId(artist.getId());
		if (artist.getImages().length > 0)
			spotifyArtist.setImage(artist.getImages()[0].getUrl());

		return spotifyArtist;
	}

	public static SpotifyArtist from(ArtistSimplified artist) {
		SpotifyArtist spotifyArtist = new SpotifyArtist();

		spotifyArtist.setName(artist.getName());
		spotifyArtist.setId(artist.getId());
		// spotifyArtist.setImage(artist.getImages()[0].getUrl());

		return spotifyArtist;
	}
}
