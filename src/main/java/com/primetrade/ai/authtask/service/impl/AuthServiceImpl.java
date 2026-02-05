package com.primetrade.ai.authtask.service.impl;

import org.springframework.stereotype.Service;

import com.primetrade.ai.authtask.dto.auth.AuthResponseDTO;
import com.primetrade.ai.authtask.dto.auth.LoginRequestDTO;
import com.primetrade.ai.authtask.dto.auth.RegisterRequestDTO;
import com.primetrade.ai.authtask.service.AuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

	@Override
	public void register(RegisterRequestDTO request) {
		// TODO Auto-generated method stub

	}

	@Override
	public AuthResponseDTO login(LoginRequestDTO request) {
		// TODO Auto-generated method stub
		return null;
	}

}
