package com.github.ioloolo.music_player.domain.track.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrackSearchRequest {

	@NotBlank
	private String query;
}
