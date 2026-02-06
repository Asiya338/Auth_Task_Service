package com.primetrade.ai.authtask.service;

import java.util.List;

import com.primetrade.ai.authtask.dto.task.TaskRequestDTO;
import com.primetrade.ai.authtask.dto.task.TaskResponseDTO;

public interface TaskService {

	TaskResponseDTO createTask(TaskRequestDTO request);

	List<TaskResponseDTO> getAllTasks();

	TaskResponseDTO updateTask(Long id, TaskRequestDTO request);

	void deleteTask(Long id);
}
