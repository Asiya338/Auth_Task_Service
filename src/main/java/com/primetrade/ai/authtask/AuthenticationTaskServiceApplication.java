package com.primetrade.ai.authtask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(info = @Info(title = "Auth Task Service API", version = "v1", description = "REST API for user authentication and task management with JWT-based security, role-based access control, and centralized exception handling."))
@SpringBootApplication
public class AuthenticationTaskServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthenticationTaskServiceApplication.class, args);
	}

}
