package com.CrmService.service;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.CrmService.dto.Message;
import com.CrmService.dto.RemakDto;
import com.CrmService.dto.TaskDto;
import com.CrmService.dto.TaskStatusCountDto;
import com.CrmService.dto.TeamTaskSummaryDto;
import com.CrmService.dto.UpdateTaskDto;

public interface TaskService {
	public Message<TaskDto> addTask(TaskDto dto);
	public Message<TaskDto> updateTask(TaskDto dto);
	public Message<TaskDto> deleteTask(int id);
	public Message<TaskDto> getTask(int id);
	public Message<Map<String, Object>> getTaskByUserId(int user_id, String status, int page, int size);
	public Message<Map<String, Object>> getAllTask(String status, int page, int size);
	public List<Message<TaskDto>> getTaskByProjectId(String project);
	public Message<Map<String, Object>> getTaskByTeam(String team, String status, int page, int size) ;
	public List<Message<TaskDto>>getTaskByusername(String username);
	 List<Message<TaskDto>> getTasksByTeamAndDateRange(String team, String creatingDate, String endDate);
	 public Message<TaskDto>updateStatus(UpdateTaskDto dto);
	 public TaskStatusCountDto getTaskStatusCounts(int user_id, String status);
	 public TaskStatusCountDto getTaskStatusCountsByTeam(String team, String status);
	 public ResponseEntity<?> pauseInProgressTasks(int user_id);
	 public List<Message<TaskDto>>getAllTaskByUsername(String username);
	 public Message<RemakDto> aadRemark(RemakDto dto);
	 public Message<List<TeamTaskSummaryDto>> getTeamTaskSummary(String teamName) ;

}
