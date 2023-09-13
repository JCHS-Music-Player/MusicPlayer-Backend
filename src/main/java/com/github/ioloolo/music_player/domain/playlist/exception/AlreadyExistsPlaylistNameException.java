package com.github.ioloolo.music_player.domain.playlist.exception;

public class AlreadyExistsPlaylistNameException extends Exception {
	public AlreadyExistsPlaylistNameException() {
		super("이미 존재하는 플레이리스트입니다.");
	}
}
