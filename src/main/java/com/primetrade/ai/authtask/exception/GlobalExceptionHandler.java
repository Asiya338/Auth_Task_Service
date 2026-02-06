package com.primetrade.ai.authtask.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.primetrade.ai.authtask.enums.ErrorCodeEnum;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

	private ErrorResponse buildErrorResponse(String errorCode, String message, HttpServletRequest request, int status) {

		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(errorCode);
		error.setMessage(message);
		error.setPath(request.getRequestURI());
		error.setHttpMethod(request.getMethod());
		error.setTraceId(MDC.get("traceId"));
		error.setStatus(status);
		error.setTimestamp(LocalDateTime.now());

		return error;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
			HttpServletRequest request) {

		String message = ex.getBindingResult().getAllErrors().get(0).getDefaultMessage();

		ErrorResponse error = buildErrorResponse(ErrorCodeEnum.VALIDATION_FAILED.name(), message, request,
				HttpStatus.BAD_REQUEST.value());

		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex,
			HttpServletRequest request) {

		log.warn("Business exception: {}", ex.getMessage());

		ErrorResponse error = buildErrorResponse(ErrorCodeEnum.BAD_REQUEST.name(), ex.getMessage(), request,
				HttpStatus.BAD_REQUEST.value());

		return ResponseEntity.badRequest().body(error);
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorResponse> handleNotFound(NoSuchElementException ex, HttpServletRequest request) {

		ErrorResponse error = buildErrorResponse(ErrorCodeEnum.RESOURCE_NOT_FOUND.name(), ex.getMessage(), request,
				HttpStatus.NOT_FOUND.value());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {

		log.error("Unhandled exception", ex);

		ErrorResponse error = buildErrorResponse(ErrorCodeEnum.INTERNAL_ERROR.name(),
				"Unexpected server error occurred", request, HttpStatus.INTERNAL_SERVER_ERROR.value());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	}
}