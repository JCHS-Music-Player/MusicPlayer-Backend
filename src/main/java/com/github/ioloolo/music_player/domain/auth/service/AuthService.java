package com.github.ioloolo.music_player.domain.auth.service;

import java.util.EnumSet;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.ioloolo.music_player.common.config.security.jwt.JwtUtil;
import com.github.ioloolo.music_player.domain.auth.exception.AlreadyTakenUsernameException;
import com.github.ioloolo.music_player.domain.auth.model.Role;
import com.github.ioloolo.music_player.domain.auth.model.User;
import com.github.ioloolo.music_player.domain.auth.repository.RoleRepository;
import com.github.ioloolo.music_player.domain.auth.repository.UserRepository;
import com.github.ioloolo.music_player.domain.history.model.History;
import com.github.ioloolo.music_player.domain.history.repository.HistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final HistoryRepository historyRepository;

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtil;

	public String login(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtUtil.generateJwtToken(authentication);
	}

	public void register(String username, String password) throws AlreadyTakenUsernameException {
		if (userRepository.existsByUsername(username)) {
			throw new AlreadyTakenUsernameException();
		}

		Role userRole = roleRepository.findByName(Role.Roles.ROLE_USER)
				.orElseThrow(IllegalCallerException::new);

		User user = User.builder()
				.username(username)
				.password(encoder.encode(password))
				.role(userRole)
				.build();
		userRepository.save(user);

		History history = History.builder()
				.user(user)
				.tracks(List.of())
				.build();
		historyRepository.save(history);
	}

	@PostConstruct
	public void init() {
		EnumSet<Role.Roles> enumSet = EnumSet.allOf(Role.Roles.class);

		for (Role.Roles role : enumSet) {
			if (roleRepository.findByName(role).isEmpty()) {
				roleRepository.save(Role.builder()
						.name(role)
						.build());
			}
		}
	}
}
