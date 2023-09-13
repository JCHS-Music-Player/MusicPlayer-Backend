package com.github.ioloolo.music_player.domain.album.data;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import com.github.ioloolo.music_player.domain.artist.data.SpotifyArtist;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;

@Data
public class SpotifyAlbum {

	private String name;
	private String id;
	private List<SpotifyArtist> artist;
	private String image;
	private LocalDate releaseDate;

	public static SpotifyAlbum from(AlbumSimplified album) {
		SpotifyAlbum spotifyAlbum = new SpotifyAlbum();

		spotifyAlbum.setName(album.getName());
		spotifyAlbum.setId(album.getId());
		spotifyAlbum.setArtist(Arrays.stream(album.getArtists()).map(SpotifyArtist::from).toList());
		spotifyAlbum.setImage(album.getImages()[0].getUrl());

		String[] split = album.getReleaseDate().split("-");
		spotifyAlbum.setReleaseDate(LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]),
				split.length >= 3 ? Integer.parseInt(split[2]) : 1));

		return spotifyAlbum;
	}
}
