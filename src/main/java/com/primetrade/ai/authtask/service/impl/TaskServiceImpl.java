package com.primetrade.ai.authtask.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.primetrade.ai.authtask.dto.task.TaskRequestDTO;
import com.primetrade.ai.authtask.dto.task.TaskResponseDTO;
import com.primetrade.ai.authtask.entity.Task;
import com.primetrade.ai.authtask.entity.User;
import com.primetrade.ai.authtask.enums.Role;
import com.primetrade.ai.authtask.repository.TaskRepository;
import com.primetrade.ai.authtask.repository.UserRepository;
import com.primetrade.ai.authtask.service.TaskService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskRepository taskRepository;
	private final UserRepository userRepository;
	private final ModelMapper modelMapper;

	private User getCurrentLoggedInUser() {

		log.info("Fetching current logged-in user");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		String email = authentication.getName();

		log.info("Current logged-in user email: {}", email);

		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("User not found with email: " + email));

	}

	@Override
	public TaskResponseDTO createTask(TaskRequestDTO request) {

		log.info("Creating task with title: {}", request.getTitle());

		User currentUser = getCurrentLoggedInUser();

		Task task = modelMapper.map(request, Task.class);
		task.setUser(currentUser);

		Task savedTask = taskRepository.save(task);

		log.info("Task created with ID: {}", savedTask.getId());

		return modelMapper.map(savedTask, TaskResponseDTO.class);

	}

	@Override
	public List<TaskResponseDTO> getAllTasks() {

		log.info("Fetching all tasks for current user");

		User currentUser = getCurrentLoggedInUser();

		List<Task> tasks = currentUser.getRole() == Role.ADMIN ? taskRepository.findAll()
				: taskRepository.findByUser(currentUser);

		log.info("Fetched {} tasks for user ID: {}", tasks.size(), currentUser.getId());

		return tasks.stream().map(task -> modelMapper.map(task, TaskResponseDTO.class)).toList();

	}

	@Override
	public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO request) {

		log.info("Updating task with ID: {}", taskId);

		Task task = taskRepository.findById(taskId)
				.orElseThrow(() -> new RuntimeException("Task not found with ID: " + taskId));

		User currentUser = getCurrentLoggedInUser();

		if (!task.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
			throw new RuntimeException("Access denied.");
		}

		modelMapper.map(request, task);

		Task updatedTask = taskRepository.save(task);

		log.info("Task with ID: {} updated successfully", taskId);

		return modelMapper.map(updatedTask, TaskResponseDTO.class);
	}

	@Override
	public void deleteTask(Long id) {

		log.info("Deleting task with ID: {}", id);

		Task task = taskRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Task not found with ID: " + id));

		User currentUser = getCurrentLoggedInUser();

		if (!task.getUser().getId().equals(currentUser.getId()) && currentUser.getRole() != Role.ADMIN) {
			throw new RuntimeException("Access denied.");
		}

		taskRepository.delete(task);

		log.info("Task with ID: {} deleted successfully", id);

	}

}
