package com.github.ioloolo.music_player.domain.history.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.github.ioloolo.music_player.domain.auth.model.User;
import com.github.ioloolo.music_player.domain.history.model.History;

public interface HistoryRepository extends MongoRepository<History, String> {
	History findByUser(User user);
}
