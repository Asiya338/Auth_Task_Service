package com.primetrade.ai.authtask.exception;

import java.time.LocalDateTime;

import org.slf4j.MDC;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primetrade.ai.authtask.constants.Constant;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			org.springframework.security.core.AuthenticationException authException)
			throws java.io.IOException, ServletException {
		ErrorResponse error = new ErrorResponse("UNAUTHORIZED", "Authentication required or token is invalid",
				request.getRequestURI(), request.getMethod(), MDC.get(Constant.MDC_TRACE_ID),
				HttpServletResponse.SC_UNAUTHORIZED, LocalDateTime.now());

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		response.getWriter().write(objectMapper.writeValueAsString(error));
	}
}
