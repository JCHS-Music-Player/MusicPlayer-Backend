package com.github.ioloolo.mongo_jwt_security.domain.auth.repository;

import java.util.Optional;

import com.github.ioloolo.mongo_jwt_security.domain.auth.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByUsername(String username);

  boolean existsByUsername(String username);

  boolean existsByEmail(String email);
}
