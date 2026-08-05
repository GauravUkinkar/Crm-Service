package com.CrmService.mapperImpl;



import java.time.LocalTime;
import java.util.Date;

import org.springframework.stereotype.Component;

import com.CrmService.dto.TaskSchedulerDto;
import com.CrmService.mapper.TaskSchedulerMapper;
import com.CrmService.model.TaskScheduler;
import com.CrmService.model.TaskStatus;


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

	    taskScheduler.setUserId(dto.getUserId());
	    taskScheduler.setStatus(TaskStatus.ONLINE);
	    taskScheduler.setLast_seen(LocalTime.now().withNano(0));
	    taskScheduler.setDate(new Date());
	    return taskScheduler;
	}

}
