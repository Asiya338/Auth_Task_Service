package com.primetrade.ai.authtask.dto.task;

import java.time.LocalDateTime;

import com.primetrade.ai.authtask.enums.TaskStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponseDTO {

	private Long id;
	private String title;
	private String description;
	private TaskStatus status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

}
