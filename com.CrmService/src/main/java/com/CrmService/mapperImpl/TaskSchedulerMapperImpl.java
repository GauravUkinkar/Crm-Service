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
		dto.setLast_seen(TaskScheduler.getLast_seen());
		dto.setDate(TaskScheduler.getDate());
		dto.setTaskId(TaskScheduler.getTaskId());
		return dto;
	}

	@Override
	public TaskScheduler toTaskScheduler(TaskSchedulerDto dto) {
	    TaskScheduler taskScheduler = new TaskScheduler();

	    taskScheduler.setId(dto.getId());
	    taskScheduler.setUserId(dto.getUserId());
	    taskScheduler.setStatus(dto.getStatus());
	    taskScheduler.setLast_seen(dto.getLast_seen());
	    taskScheduler.setDate(dto.getDate());
	    taskScheduler.setDate(dto.getDate());
	    taskScheduler.setTaskId(dto.getTaskId());
	    return taskScheduler;
	}

}
