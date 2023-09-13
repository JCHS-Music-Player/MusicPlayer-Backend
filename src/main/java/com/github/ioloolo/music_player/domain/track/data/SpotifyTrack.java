package com.github.ioloolo.music_player.domain.track.data;

import java.util.Arrays;
import java.util.List;

import com.github.ioloolo.music_player.domain.album.data.SpotifyAlbum;
import com.github.ioloolo.music_player.domain.artist.data.SpotifyArtist;

import lombok.Data;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.model_objects.specification.TrackSimplified;

@Data
public class SpotifyTrack {

	private String name;
	private String id;
	private SpotifyAlbum album;
	private List<SpotifyArtist> artists;
	private String image;

	public static SpotifyTrack from(Track track) {
		SpotifyTrack spotifyTrack = new SpotifyTrack();

		spotifyTrack.setName(track.getName());
		spotifyTrack.setId(track.getId());
		spotifyTrack.setAlbum(SpotifyAlbum.from(track.getAlbum()));
		spotifyTrack.setArtists(Arrays.stream(track.getArtists())
				.map(SpotifyArtist::from)
				.toList());
		spotifyTrack.setImage(track.getAlbum().getImages()[0].getUrl());

		return spotifyTrack;
	}

	public static SpotifyTrack from(TrackSimplified track) {
		SpotifyTrack spotifyTrack = new SpotifyTrack();

		spotifyTrack.setName(track.getName());
		spotifyTrack.setId(track.getId());

		return spotifyTrack;
	}
}
