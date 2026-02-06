package com.primetrade.ai.authtask.dto.task;

import com.primetrade.ai.authtask.enums.TaskStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskRequestDTO {

	@NotBlank(message = "Title is required")
	@Size(max = 200, message = "Title must be less than 200 characters")
	private String title;

	@Size(max = 500, message = "Description must be less than 500 characters")
	private String description;

	@NotNull(message = "Task status is required")
	private TaskStatus status;
}
