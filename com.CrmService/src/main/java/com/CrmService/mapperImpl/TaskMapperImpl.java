package com.CrmService.mapperImpl;


import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.stereotype.Component;

import com.CrmService.dto.TaskDto;
import com.CrmService.mapper.TaskMapper;
import com.CrmService.model.TaskTracking;

@Component
public class TaskMapperImpl implements TaskMapper {
	
	   
	@Override
	public TaskDto toTaskDto(TaskTracking task) {
		TaskDto dto=new TaskDto();
		dto.setCategory(task.getCategory());
		dto.setClient(task.getClient());
		dto.setDedline(task.getDedline());
		dto.setAssingedBy(task.getAssingedBy());
		dto.setDescription(task.getDescription());
		dto.setId(task.getId());
		dto.setProject(task.getProject());
		dto.setStartingDate(task.getStartingDate());
		dto.setStatus(task.getStatus());
		dto.setTitle(task.getTitle());
		dto.setTeam(task.getTeam());
		dto.setUsername(task.getUsername());
		dto.setCreatedDate(task.getCreatedDate());
		dto.setTotalHours(task.getTotalHours());
		dto.setCreatingDate(task.getCreatingDate());
		dto.setStartworkingHours(task.getStartworkingHours());
		dto.setEndworkingHours(task.getEndworkingHours());
		dto.setTotalHours(task.getTotalHours());
		dto.setDeletedTag(task.getDeletedTag());
		dto.setRemarks(task.getRemarks());
		dto.setEmployeeName(task.getEmployeeName());
		dto.setEndDate(task.getEndDate());
		dto.setCreatingDate(task.getCreatingDate());
		return dto;
	}

	@Override
	public TaskTracking toTaskTrackerEntity(TaskDto dto) {
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		LocalDateTime localDateTime = LocalDateTime.now(zoneId);
		Timestamp timestamp = Timestamp.valueOf(localDateTime);
		TaskTracking entity=new TaskTracking();
		entity.setClient(dto.getClient());
		entity.setCategory(dto.getCategory());
		entity.setDedline(dto.getDedline());
		entity.setAssingedBy(dto.getAssingedBy());
		entity.setDescription(dto.getDescription());
		entity.setProject(dto.getProject());
		entity.setCreatedDate(timestamp);
		entity.setStatus("Pending");
		entity.setTitle(dto.getTitle());
		entity.setTeam(dto.getTeam());
		entity.setUsername(dto.getUsername());
		entity.setCreatingDate(dto.getCreatingDate());
		entity.setDeletedTag("False");
		entity.setRemarks(dto.getRemarks());
		entity.setRemarks(dto.getRemarks());
		entity.setEmployeeName(dto.getEmployeeName());
		
		return entity;
	}
//	  public TaskTracking toTaskTrackerEntity(TaskDto dto, TaskTracking existingTask) {
//	        TaskTracking task = existingTask != null ? existingTask : new TaskTracking();
//	        ZoneId zoneId = ZoneId.of("Asia/Kolkata");
//			LocalDateTime localDateTime = LocalDateTime.now(zoneId);
//			Timestamp timestamp = Timestamp.valueOf(localDateTime);
//	        // Map fields from DTO to Entity
//	        task.setClient(dto.getClient());
//	        task.setTitle(dto.getTitle());
//	        task.setCategory(dto.getCategory());
//	        task.setDescription(dto.getDescription());
//	        task.setDedline(dto.getDedline());
//	        task.setStartingDate(timestamp);
//	        task.setStatus(dto.getStatus());
//	        task.setProject(dto.getProject());
//	        task.setAssingedBy(dto.getAssingedBy());
//	        task.setTeam(dto.getTeam());
//	        task.setUsername(dto.getUsername());
//	        task.setCreatedDate(timestamp);
//	        task.setRemarks(dto.getRemarks());
//	        task.setCreatingDate(dto.getCreatingDate());
////	        task.setCreatedDate(new Date());
//
//	        // Preserve user if existingTask is provided
//	        if (existingTask != null) {
//	            task.setUser(existingTask.getUser());
//	        }
//
//	        return task;
//	    }

}
