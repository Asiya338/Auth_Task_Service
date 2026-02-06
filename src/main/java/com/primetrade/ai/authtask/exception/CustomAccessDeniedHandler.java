package com.primetrade.ai.authtask.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import org.slf4j.MDC;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.primetrade.ai.authtask.constants.Constant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private final ObjectMapper objectMapper = new ObjectMapper();

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
			throws IOException {

		ErrorResponse error = new ErrorResponse("FORBIDDEN", "You do not have permission to access this resource",
				request.getRequestURI(), request.getMethod(), MDC.get(Constant.MDC_TRACE_ID),
				HttpServletResponse.SC_FORBIDDEN, LocalDateTime.now());

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setContentType("application/json");
		response.getWriter().write(objectMapper.writeValueAsString(error));
	}
}
