package com.primetrade.ai.authtask.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

	@GetMapping("/test")
	public String testEndpoint() {
		log.info("Test endpoint called");

		return "Task Service is up and running!";
	}
}
