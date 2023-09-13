package com.github.ioloolo.music_player.domain.playlist.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.github.ioloolo.music_player.domain.auth.model.User;
import com.github.ioloolo.music_player.domain.playlist.model.Playlist;

@Repository
public interface PlaylistRepository extends MongoRepository<Playlist, String> {
	Optional<Playlist> findByUserAndId(User user, String id);
	boolean existsByUserAndName(User user, String name);
}
