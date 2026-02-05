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
		// TODO Auto-generated method stub
		return null;
	}

}
