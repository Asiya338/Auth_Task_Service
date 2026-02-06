package com.primetrade.ai.authtask.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.primetrade.ai.authtask.dto.task.TaskRequestDTO;
import com.primetrade.ai.authtask.dto.task.TaskResponseDTO;
import com.primetrade.ai.authtask.service.TaskService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/tasks")
@RequiredArgsConstructor
public class TaskController {

	private final TaskService taskService;

	@PostMapping
	public ResponseEntity<TaskResponseDTO> createTask(@Valid @RequestBody TaskRequestDTO request) {

		log.info("Creating task with title: {}", request.getTitle());

		TaskResponseDTO response = taskService.createTask(request);

		log.info("Task created with ID: {}", response.getId());

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@GetMapping
	public ResponseEntity<List<TaskResponseDTO>> getTasks() {

		log.info("Fetching all tasks");

		List<TaskResponseDTO> tasks = taskService.getAllTasks();

		log.info("Fetched {} tasks", tasks.size());

		return ResponseEntity.ok(tasks);
	}

	@PutMapping("/{id}")
	public ResponseEntity<TaskResponseDTO> updateTask(@PathVariable Long id,
			@Valid @RequestBody TaskRequestDTO request) {

		log.info("Updating task with ID: {}", id);

		TaskResponseDTO response = taskService.updateTask(id, request);

		log.info("Task with ID: {} updated successfully", id);

		return ResponseEntity.ok(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTask(@PathVariable Long id) {

		log.info("Deleting task with ID: {}", id);

		taskService.deleteTask(id);

		log.info("Task with ID: {} deleted successfully", id);

		return ResponseEntity.noContent().build();
	}

}
