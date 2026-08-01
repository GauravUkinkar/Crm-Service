package com.CrmService.mapperImpl;

import org.springframework.stereotype.Component;

import com.CrmService.dto.TaskSchedulerDto;
import com.CrmService.mapper.TaskSchedulerMapper;
import com.CrmService.model.TaskScheduler;

@Component
public class TaskSchedulerMapperImpl implements TaskSchedulerMapper {

	@Override
	public TaskSchedulerDto toTaskSchedulerDto(TaskScheduler TaskScheduler) {
		TaskSchedulerDto dto = new TaskSchedulerDto();
		dto.setId(TaskScheduler.getId());
		dto.setUserId(TaskScheduler.getUserId());
		dto.setStatus(TaskScheduler.getStatus());
		dto.setLast_seen(TaskScheduler.getLast_seen());
		dto.setDate(TaskScheduler.getDate());
		return dto;
	}

	@Override
	public TaskScheduler toTaskScheduler(TaskSchedulerDto dto) {
		TaskScheduler TaskScheduler = new TaskScheduler();
		TaskScheduler.setId(dto.getId());
		TaskScheduler.setUserId(dto.getUserId());
		TaskScheduler.setStatus(dto.getStatus());
		TaskScheduler.setLast_seen(dto.getLast_seen());
		TaskScheduler.setDate(dto.getDate());
		return TaskScheduler;
	}

}
