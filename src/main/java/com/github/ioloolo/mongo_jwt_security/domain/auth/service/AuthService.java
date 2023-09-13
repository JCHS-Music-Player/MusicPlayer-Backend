package com.github.ioloolo.mongo_jwt_security.domain.auth.service;

import com.github.ioloolo.mongo_jwt_security.domain.auth.model.Role;
import com.github.ioloolo.mongo_jwt_security.domain.auth.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.github.ioloolo.mongo_jwt_security.common.config.security.jwt.JwtUtil;
import com.github.ioloolo.mongo_jwt_security.domain.auth.exception.AlreadyTakenEmailException;
import com.github.ioloolo.mongo_jwt_security.domain.auth.exception.AlreadyTakenUsernameException;
import com.github.ioloolo.mongo_jwt_security.domain.auth.repository.RoleRepository;
import com.github.ioloolo.mongo_jwt_security.domain.auth.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import javax.annotation.PostConstruct;
import java.util.EnumSet;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	private final AuthenticationManager authenticationManager;
	private final PasswordEncoder encoder;
	private final JwtUtil jwtUtil;

	public String login(String username, String password) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
		Authentication authentication = authenticationManager.authenticate(authenticationToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		return jwtUtil.generateJwtToken(authentication);
	}

	public void register(String username, String email, String password)
			throws AlreadyTakenUsernameException, AlreadyTakenEmailException {

		if (userRepository.existsByUsername(username)) {
			throw new AlreadyTakenUsernameException();
		}

		if (userRepository.existsByEmail(email)) {
			throw new AlreadyTakenEmailException();
		}

		Role userRole = roleRepository.findByName(Role.Roles.ROLE_USER)
				.orElseThrow(IllegalCallerException::new);

		User user = User.builder()
				.username(username)
				.email(email)
				.password(encoder.encode(password))
				.role(userRole)
				.build();

		userRepository.save(user);
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
