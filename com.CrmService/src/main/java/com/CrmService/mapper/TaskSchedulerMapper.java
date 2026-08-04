package com.CrmService.mapper;

import com.CrmService.dto.TaskSchedulerDto;
import com.CrmService.model.TaskScheduler;

public interface TaskSchedulerMapper {
	
	public TaskSchedulerDto toTaskSchedulerDto(TaskScheduler TaskScheduler);
    public TaskScheduler toTaskScheduler(TaskSchedulerDto taskSchedulerDto);
}
