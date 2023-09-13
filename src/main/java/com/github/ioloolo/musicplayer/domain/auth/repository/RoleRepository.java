package com.github.ioloolo.musicplayer.domain.auth.repository;

import java.util.Optional;

import com.github.ioloolo.musicplayer.domain.auth.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {

  Optional<Role> findByName(Role.Roles name);
}
