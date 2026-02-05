package com.primetrade.ai.authtask.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.primetrade.ai.authtask.constants.Role;
import com.primetrade.ai.authtask.dto.auth.AuthResponseDTO;
import com.primetrade.ai.authtask.dto.auth.LoginRequestDTO;
import com.primetrade.ai.authtask.dto.auth.RegisterRequestDTO;
import com.primetrade.ai.authtask.entity.User;
import com.primetrade.ai.authtask.repository.UserRepository;
import com.primetrade.ai.authtask.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	private final ModelMapper modelMapper;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void register(RegisterRequestDTO request) {
		log.info("Registering user with email | Auth Service : {}", request.getEmail());

		if (userRepository.existsByEmail(request.getEmail())) {

			log.warn("User with email {} already exists | Auth Service", request.getEmail());

			throw new RuntimeException("User with this email already exists");
		}

		if (userRepository.existsByPhoneNumber(request.getPhoneNumber())) {

			log.warn("User with phone number {} already exists | Auth Service", request.getPhoneNumber());

			throw new RuntimeException("User with this phone number already exists");
		}

		User user = modelMapper.map(request, User.class);
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(Role.USER);

		userRepository.save(user);

	}

	@Override
	public AuthResponseDTO login(LoginRequestDTO request) {

		log.info("Login for email | Auth Service : {}", request.getEmail());

		User user = userRepository.findAll().stream().filter(u -> u.getEmail().equals(request.getEmail())).findFirst()
				.orElse(null);

		if (user == null) {

			log.warn("User with email {} not found | Auth Service", request.getEmail());

			throw new RuntimeException("User not found with Email");
		}

		if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {

			log.warn("Invalid password for email {} | Auth Service", request.getEmail());

			throw new RuntimeException("Invalid password");
		}

		AuthResponseDTO response = new AuthResponseDTO();
		response.setAccessToken("temp-token");
		response.setTokenType("Bearer");
		response.setExpiresIn(3600L); // Example expiration time in seconds

		log.info("User with email {} logged in successfully | Auth Service", request.getEmail());

		return response;

	}

}
