package com.github.ioloolo.music_player.domain.history.model;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.github.ioloolo.music_player.domain.auth.model.User;
import com.github.ioloolo.music_player.domain.track.data.SpotifyTrack;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Document(collection = "histories")
public class History {

	@Id
	private String id;

	@DBRef
	private User user;

	private List<SpotifyTrack> tracks;
}
