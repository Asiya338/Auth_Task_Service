package com.primetrade.ai.authtask.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDTO {

	private String accessToken;
	private String tokenType;
	private Long expiresIn;
}
