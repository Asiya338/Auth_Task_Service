package com.primetrade.ai.authtask.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primetrade.ai.authtask.dto.auth.AuthResponseDTO;
import com.primetrade.ai.authtask.dto.auth.LoginRequestDTO;
import com.primetrade.ai.authtask.dto.auth.RegisterRequestDTO;
import com.primetrade.ai.authtask.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequestDTO request) {

		log.info("Registering user with email: {}", request.getEmail());

		authService.register(request);

		log.info("User registered successfully with email: {}", request.getEmail());

		return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {

		log.info("User login attempt with email: {}", request.getEmail());

		AuthResponseDTO response = authService.login(request);

		log.info("User logged in successfully with email: {}", request.getEmail());

		return ResponseEntity.ok(response);
	}

}
