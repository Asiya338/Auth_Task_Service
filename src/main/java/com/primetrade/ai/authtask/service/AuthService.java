package com.primetrade.ai.authtask.service;

import com.primetrade.ai.authtask.dto.auth.AuthResponseDTO;
import com.primetrade.ai.authtask.dto.auth.LoginRequestDTO;
import com.primetrade.ai.authtask.dto.auth.RegisterRequestDTO;

public interface AuthService {

	void register(RegisterRequestDTO request);

	AuthResponseDTO login(LoginRequestDTO request);

}
