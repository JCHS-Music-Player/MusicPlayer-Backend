package com.github.ioloolo.music_player.domain.artist.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArtistTopTrackRequest {

	@NotBlank
	private String id;
}
