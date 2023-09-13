package com.github.ioloolo.music_player.domain.playlist.payload.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPlaylistTrackRequest {

	@NotBlank
	private String playlist;
}
