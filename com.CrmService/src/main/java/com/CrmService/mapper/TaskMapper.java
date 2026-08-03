package com.CrmService.mapper;


import com.CrmService.dto.TaskDto;
import com.CrmService.model.TaskTracking;

public interface TaskMapper {
	public TaskDto toTaskDto(TaskTracking task);
	 public TaskTracking toTaskTrackerEntity(TaskDto dto);
//	 public TaskTracking toTaskTrackerEntity(TaskDto dto, TaskTracking existingTask);
	 	
}
