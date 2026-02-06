package com.primetrade.ai.authtask.exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

	private String errorCode;
	private String message;
	private String path;
	private String httpMethod;
	private String traceId;

	private int status;
	private LocalDateTime timestamp;
}
