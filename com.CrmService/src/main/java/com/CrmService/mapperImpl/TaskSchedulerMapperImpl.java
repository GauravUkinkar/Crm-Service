package com.CrmService.mapperImpl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

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
		dto.setLast_seen(LocalTime.now());
		dto.setDate(new Date());
		return dto;
	}

	@Override
	public TaskScheduler toTaskScheduler(TaskSchedulerDto dto) {
		TaskScheduler TaskScheduler = new TaskScheduler();
		TaskScheduler.setId(dto.getId());
		TaskScheduler.setUserId(dto.getUserId());
		TaskScheduler.setStatus(dto.getStatus());
		dto.setLast_seen(LocalTime.now());
		dto.setDate(new Date());
		return TaskScheduler;
	}

}
