package com.CrmService.serviceImpl;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.CrmService.dto.Message;
import com.CrmService.dto.RemakDto;
import com.CrmService.dto.TaskDto;
import com.CrmService.dto.TaskStatusCountDto;
import com.CrmService.dto.TeamTaskSummaryDto;
import com.CrmService.dto.UpdateTaskDto;
import com.CrmService.mapper.TaskMapper;
import com.CrmService.model.Client;
import com.CrmService.model.TaskTracking;
import com.CrmService.model.Team;
import com.CrmService.repository.ClientRepository;
import com.CrmService.repository.ProjectsRepository;
import com.CrmService.repository.TaskRepository;
import com.CrmService.repository.TeamRepository;
import com.CrmService.service.TaskService;
import com.CrmService.util.Constants;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

	private final TaskMapper taskMapper;
	private final TaskRepository taskRepository;
	private final ProjectsRepository projectRepository;
	public final ClientRepository repository;
	private final TeamRepository teamRepository;
	private final ClientRepository clientRepository;

	@Override
	public Message<TaskDto> addTask(TaskDto dto) {
		Message<TaskDto> message = new Message<>();
//		 String username/email=jwtService.extractemail(token);
		log.info("Incoming request body: {}", dto);
		try {


//			List<String> projectNames = Arrays.stream(dto.getProject().split(",")).map(String::trim)
//					.collect(Collectors.toList());
//
//			List<Project> projects = projectRepository.findByNameIn(projectNames);
//			if (projects.isEmpty()) {
//				message.setStatus(HttpStatus.CONFLICT);
//				message.setResponseMessage(Constants.PROJECT_NOT_FOUND);
//				log.error("Error in addTask(): {}", message.getResponseMessage());
//				return message;
//			}

// Optionally log which project names were not found
//			List<String> foundNames = projects.stream().map(Project::getName).collect(Collectors.toList());
//			List<String> notFound = projectNames.stream().filter(name -> !foundNames.contains(name))
//					.collect(Collectors.toList());
//			if (!notFound.isEmpty()) {
//				log.warn("The following projects were not found: {}", notFound);
//			}

			Client client = clientRepository.getByName(dto.getClient());
			if (client == null) {
				message.setStatus(HttpStatus.CONFLICT);
				message.setResponseMessage(Constants.CLIENT_NOT_FOUND);
				log.error("Error in addTask(): {}", message.getResponseMessage());
				return message;
			}

			Team team = teamRepository.getByName(dto.getTeam());
			if (team == null) {
				message.setStatus(HttpStatus.CONFLICT);
				message.setResponseMessage(Constants.TEAM_NOT_FOUND);
				log.error("Error in addTask(): {}", message.getResponseMessage());
				return message;
			}

//			User user = userRepository.getByEmail(dto.getUsername());
			if (user == null) {
				message.setStatus(HttpStatus.CONFLICT);
				message.setResponseMessage(Constants.USER_NOT_FOUND);
				log.error("Error in addTask(): {}", message.getResponseMessage());
				return message;
			}

			TaskTracking task = taskMapper.toTaskTrackerEntity(dto);
			task.setUser(user); // Associate the task with the user
			TaskTracking savedTask = taskRepository.save(task);

			TaskDto responseDto = taskMapper.toTaskDto(savedTask);
			message.setStatus(HttpStatus.CREATED);
			message.setResponseMessage(Constants.TASK_ADD);
			message.setData(responseDto);
			log.info("Task added successfully with request: {}", dto);
			return message;
		} catch (Exception e) {
			log.error("In addTask() exception: ", e);
			message.setResponseMessage("Failed to add task: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	public Message<TaskDto> updateTask(TaskDto dto) {
		Message<TaskDto> message = new Message<>();
		log.info("Incoming request body: {}", dto);
		try {
			// Check if the project exists
//			List<String> projectNames = Arrays.stream(dto.getProject().split(",")).map(String::trim)
//					.collect(Collectors.toList());
//
//			List<Project> projects = projectRepository.findByNameIn(projectNames);
//			if (projects.isEmpty()) {
//				message.setStatus(HttpStatus.CONFLICT);  
//				message.setResponseMessage(Constants.PROJECT_NOT_FOUND);
//				log.error("Error in addTask(): {}", message.getResponseMessage());
//				return message;
//			}
//
//// Optionally log which project names were not found
//			List<String> foundNames = projects.stream().map(Project::getName).collect(Collectors.toList());
//			List<String> notFound = projectNames.stream().filter(name -> !foundNames.contains(name))
//					.collect(Collectors.toList());
//			if (!notFound.isEmpty()) {
//				log.warn("The following projects were not found: {}", notFound);
//			}


			// Check if the client exists
			Client cli = repository.getByName(dto.getClient());
			if (cli == null) {
				message.setResponseMessage(Constants.CLIENT_NOT_FOUND);
				message.setStatus(HttpStatus.CONFLICT);
				log.error("Error facing in AddClient(): {}", message.getResponseMessage());
				return message;
			}

			// Check if the task exists
			TaskTracking existingTask = taskRepository.getById(dto.getId());
			if (existingTask == null) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("Task with ID " + dto.getId() + " not found.");
				log.error("Error: Task not found for update with ID: {}", dto.getId());
				return message;
			}

			// Map DTO to entity, preserving user
			TaskTracking taskToUpdate = taskMapper.toTaskTrackerEntity(dto, existingTask);

			// Save updated entity
			TaskTracking updatedTask = taskRepository.save(taskToUpdate);

			// Convert updated task to DTO
			TaskDto updatedTaskDto = taskMapper.toTaskDto(updatedTask);

			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.TASK_UPDATE);
			message.setData(updatedTaskDto);

			log.info("Task updated successfully with ID: {}", dto.getId());
			return message;
		} catch (Exception e) {
			log.error("In TaskService updateTask() exception: ", e);
			message.setResponseMessage("Failed to update task: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<TaskDto> deleteTask(int id) {
		Message<TaskDto> message = new Message<>();
		log.info("Incoming request with taskID: {}", id);
		TaskTracking task = taskRepository.getById(id);
		try {
			// Check if the task exists
			if (task == null || task.getDeletedTag().equalsIgnoreCase("True")) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(Constants.TASK_NOT_FOUND);
				log.error("Error: Task not found for deletion with ID: {}", id);
				return message;
			}

			task.setDeletedTag("True");
			taskRepository.save(task);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.TASK_DELETE);
			log.info("Task deleted successfully with request: {}", id);
			return message;
		} catch (Exception e) {
			log.error("In TaskService deleteTask() exception: ", e);
			message.setResponseMessage("Failed to delete task: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<TaskDto> getTask(int id) {
		Message<TaskDto> message = new Message<>();
		log.info("Incoming request with task ID: {}", id);

		try {
			// Fetch the task from the repository using the provided id
			TaskTracking task = taskRepository.getById(id);

			// Check if the task is null (not found)
			if (task == null || task.getDeletedTag().equalsIgnoreCase("True")) {
				log.error("Task not found for ID: {}", id);
				message.setResponseMessage("Task not found for ID: " + id);
				message.setStatus(HttpStatus.NOT_FOUND);
				return message;
			}

			// Task is found, map the task entity to TaskDto
			TaskDto taskDto = taskMapper.toTaskDto(task);

			// Prepare the success response
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("Task found successfully.");
			message.setData(taskDto);
			return message;

		} catch (Exception e) {
			// Handle any unexpected exceptions
			log.error("Failed to fetch task with ID: {}. Exception: {}", id, e.getMessage());
			message.setResponseMessage("Failed to fetch task: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}
	@Override
	public Message<Map<String, Object>> getAllTask(String status, int page, int size) {
	    Message<Map<String, Object>> message = new Message<>();
	    try {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
	        Page<TaskTracking> taskPage;

	        if (status != null && !status.trim().isEmpty() && !"null".equalsIgnoreCase(status.trim())) {
	            taskPage = taskRepository.findByStatusAndDeletedTagNotIgnoreCase(status.trim(), "True", pageable);
	        } else {
	            taskPage = taskRepository.findByDeletedTagNotIgnoreCase("True", pageable);
	        }

	        List<TaskDto> taskDtos = taskPage.getContent().stream()
	                .map(taskMapper::toTaskDto)
	                .toList();

	        // Use LinkedHashMap to preserve order
	        Map<String, Object> responseData = new LinkedHashMap<>();
	        responseData.put("tasks", taskDtos);                  // tasks first
	        responseData.put("totalItems", taskPage.getTotalElements());
	        responseData.put("totalPages", taskPage.getTotalPages());
	        responseData.put("currentPage", taskPage.getNumber());

	        message.setStatus(HttpStatus.OK);
	        message.setResponseMessage("Tasks fetched successfully");
	        message.setData(responseData);

	        return message;

	    } catch (Exception e) {
	        log.error("In TaskService getAllTask() exception: ", e);
	        message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        message.setResponseMessage("Failed to fetch tasks: " + e.getMessage());
	        return message;
	    }
	}



	@Override
	public List<Message<TaskDto>> getTaskByProjectId(String project) {
		List<Message<TaskDto>> messages = new ArrayList<>();
		log.info("Incoming request to fetch tasks for project: {}", project);

		try {
			// Fetch the tasks by project name (assuming taskRepository.findByProject()
			// returns a list of tasks)
			List<TaskTracking> tasks = taskRepository.getByProject(project);

			// Check if tasks were found for the given project
			tasks = tasks.stream().filter(task -> !"True".equalsIgnoreCase(task.getDeletedTag()))
					.collect(Collectors.toList());

			// Check if tasks were found for the given project
			if (tasks.isEmpty()) {
				Message<TaskDto> message = new Message<>();
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("No tasks found for project: " + project);
				log.error("No tasks found for project: {}", project);
				messages.add(message);
				return messages;
			}

			// If tasks are found, map each task to a TaskDto
			for (TaskTracking task : tasks) {
				TaskDto taskDto = taskMapper.toTaskDto(task);

				Message<TaskDto> message = new Message<>();
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage("Tasks found successfully.");
				message.setData(taskDto);
				messages.add(message);
			}
		} catch (Exception e) {
			// Handle any unexpected exceptions
			log.error("Failed to fetch tasks for project: {}. Exception: {}" + e.getMessage());
			Message<TaskDto> errorMessage = new Message<>();
			errorMessage.setResponseMessage("Failed to fetch tasks: " + e.getMessage());
			errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(errorMessage);
			return messages;
		}
		return messages;
	}
	@Override
	public Message<Map<String, Object>> getTaskByTeam(String team, String status, int page, int size) {
	    Message<Map<String, Object>> message = new Message<>();
	    log.info("Incoming request to fetch tasks for team: {} with status: {}", team, status);

	    try {
	        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
	        Page<TaskTracking> taskPage;

	        if (status != null && !status.trim().isEmpty() && !"null".equalsIgnoreCase(status.trim())) {
	            taskPage = taskRepository.findByTeamAndStatusAndDeletedTagNotIgnoreCase(team, status.trim(), "True", pageable);
	        } else {
	            taskPage = taskRepository.findByTeamAndDeletedTagNotIgnoreCase(team, "True", pageable);
	        }

	        if (taskPage.isEmpty()) {
	            message.setStatus(HttpStatus.NOT_FOUND);
	            message.setResponseMessage(
	                (status == null || "null".equalsIgnoreCase(status))
	                    ? "No tasks found for team: " + team
	                    : "No tasks found for team: " + team + " with status: " + status
	            );

	            // Use LinkedHashMap to preserve order
	            Map<String, Object> emptyData = new LinkedHashMap<>();
	            emptyData.put("tasks", List.of());
	            emptyData.put("totalItems", 0);
	            emptyData.put("totalPages", 0);
	            emptyData.put("currentPage", page);

	            message.setData(emptyData);
	            log.warn(message.getResponseMessage());
	            return message;
	        }

	        List<TaskDto> taskDtos = taskPage.getContent().stream()
	                .map(taskMapper::toTaskDto)
	                .toList();

	        // Preserve order: tasks first, then pagination info
	        Map<String, Object> responseData = new LinkedHashMap<>();
	        responseData.put("tasks", taskDtos);
	        responseData.put("totalItems", taskPage.getTotalElements());
	        responseData.put("totalPages", taskPage.getTotalPages());
	        responseData.put("currentPage", taskPage.getNumber());

	        message.setStatus(HttpStatus.OK);
	        message.setResponseMessage("Tasks fetched successfully for team: " + team);
	        message.setData(responseData);

	    } catch (Exception e) {
	        log.error("Failed to fetch tasks for team: {}. Exception: {}", team, e.getMessage(), e);
	        message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        message.setResponseMessage("Failed to fetch tasks: " + e.getMessage());
	    }

	    return message;
	}


	@Override
	public List<Message<TaskDto>> getTaskByusername(String username) {
		List<Message<TaskDto>> messages = new ArrayList<>();
		log.info("Incoming request to fetch tasks for user: {}", username);

		try {
			// Fetch tasks for the given username
			List<TaskTracking> tasks = taskRepository.getByUsername(username);
			log.info("Fetched {} tasks for username: {}", tasks.size(), username);

			// Filter tasks where deletedTag is not "True"
			tasks = tasks.stream().filter(task -> !"True".equalsIgnoreCase(task.getDeletedTag()))
					.collect(Collectors.toList());

			log.info("{} tasks remain after filtering deletedTag for username: {}", tasks.size(), username);

			// Check if tasks were found for the given username
			if (tasks.isEmpty()) {
				Message<TaskDto> message = new Message<>();
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("No tasks found for user: " + username);
				log.warn("No tasks found for user: {}", username);
				messages.add(message);
				return messages;
			}

			// Map each task to a TaskDto and add to the messages list
			for (TaskTracking task : tasks) {
				String deadlineStr = task.getDedline();
				LocalDate deadline = LocalDate.parse(deadlineStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		        LocalDate today = LocalDate.now();

		        if (today.isAfter(deadline) 
		                && !task.getStatus().equals("Completed")) {
		        	
		
			        task.setStatus("Delayed");
			        taskRepository.save(task);
			        
		        }
			    
				TaskDto taskDto = taskMapper.toTaskDto(task);

				Message<TaskDto> message = new Message<>();
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage("Task found successfully.");
				message.setData(taskDto);
				messages.add(message);
			}

			// Return all task messages
			return messages;
		} catch (Exception e) {
			// Handle any unexpected exceptions
			log.error("Failed to fetch tasks for user: {}. Exception: {}", username, e.getMessage(), e);
			Message<TaskDto> errorMessage = new Message<>();
			errorMessage.setResponseMessage("Failed to fetch tasks: " + e.getMessage());
			errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(errorMessage);
			return messages;
		}
	}
//	private static final Set<String> ALLOWED_STATUSES = 
//	        new HashSet<>(Arrays.asList("paused", "pending", "inprogress", "completed"));
//	@Override
	@Override
	public Message<Map<String, Object>> getTaskByUserId(int user_id, String status, int page, int size) {
	    Message<Map<String, Object>> message = new Message<>();
	    log.info("Incoming request to fetch tasks for user: {} with status: {}", user_id, status);

	    try {
	        Pageable pageable = PageRequest.of(page- 1, size, Sort.by(Sort.Direction.DESC, "createdDate"));
	        Page<TaskTracking> taskPage;

	        if (status != null && !status.trim().isEmpty() && !"null".equalsIgnoreCase(status.trim())) {
	            taskPage = taskRepository.findByUser_IdAndStatusAndDeletedTagNotIgnoreCase(
	                    user_id, status.trim(), "True", pageable);
	        } else {
	            taskPage = taskRepository.findByUser_IdAndDeletedTagNotIgnoreCase(user_id, "True", pageable);
	        }

	        if (taskPage.isEmpty()) {
	            message.setStatus(HttpStatus.NOT_FOUND);
	            message.setResponseMessage(
	                    (status == null || "null".equalsIgnoreCase(status))
	                            ? "No tasks found for user: " + user_id
	                            : "No tasks found for user: " + user_id + " with status: " + status
	            );

	            // Use LinkedHashMap to preserve order
	            Map<String, Object> emptyData = new LinkedHashMap<>();
	            emptyData.put("tasks", List.of());
	            emptyData.put("totalItems", 0);
	            emptyData.put("totalPages", 0);
	            emptyData.put("currentPage", page);

	            message.setData(emptyData);
	            log.warn(message.getResponseMessage());
	            return message;
	        }

	        // Map tasks to DTOs
	        List<TaskDto> taskDtos = taskPage.getContent().stream()
	                .sorted(Comparator.comparing(TaskTracking::getCreatedDate).reversed())
	                .map(taskMapper::toTaskDto)
	                .toList();

	        // Preserve order: tasks first, then pagination info
	        Map<String, Object> responseData = new LinkedHashMap<>();
	        responseData.put("tasks", taskDtos);
	        responseData.put("totalItems", taskPage.getTotalElements());
	        responseData.put("totalPages", taskPage.getTotalPages());
	        responseData.put("currentPage", taskPage.getNumber() + 1);

	        message.setStatus(HttpStatus.OK);
	        message.setResponseMessage("Tasks fetched successfully for user: " + user_id);
	        message.setData(responseData);

	    } catch (Exception e) {
	        log.error("Failed to fetch tasks for user: {}. Exception: {}", user_id, e.getMessage(), e);
	        message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        message.setResponseMessage("Failed to fetch tasks: " + e.getMessage());
	    }

	    return message;
	}

	@Override
	public List<Message<TaskDto>> getTasksByTeamAndDateRange(String team, String creatingDate, String endDate) {
		List<Message<TaskDto>> messages = new ArrayList<>();
		log.info("Incoming request to fetch tasks for team '{}' from {} to {}", team, creatingDate, endDate);

		try {
			// Validate the input parameters
			if (team == null || team.isEmpty()) {
				Message<TaskDto> errorMessage = new Message<>();
				errorMessage.setStatus(HttpStatus.BAD_REQUEST);
				errorMessage.setResponseMessage("Team must be provided.");
				log.error("Invalid team: {}", team);
				messages.add(errorMessage);
				return messages;
			}
			if (creatingDate == null || endDate == null) {
				Message<TaskDto> errorMessage = new Message<>();
				errorMessage.setStatus(HttpStatus.BAD_REQUEST);
				errorMessage.setResponseMessage("Start date and end date must be provided.");
				log.error("Invalid date range: startDate={}, endDate={}", creatingDate, endDate);
				messages.add(errorMessage);
				return messages;
			}

			// Fetch tasks based on team and date range
			List<TaskTracking> tasks = taskRepository.findByTeamAndCreatingDateBetween(team, creatingDate, endDate);

			// Check if tasks were found for the given criteria
			if (tasks == null || tasks.isEmpty()) {
				Message<TaskDto> message = new Message<>();
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(
						"No tasks found for team '" + team + "' between " + creatingDate + " and " + endDate);
				log.warn("No tasks found for team '{}' between {} and {}", team, creatingDate, endDate);
				messages.add(message);
				return messages;
			}

			// Filter tasks where deletedTag is not "True"
			for (TaskTracking task : tasks) {
				// Check if the task is not marked as deleted (i.e., deletedTag != "True")
				if (task.getDeletedTag() != null && !task.getDeletedTag().equalsIgnoreCase("True")) {
					TaskDto taskDto = taskMapper.toTaskDto(task);

					Message<TaskDto> message = new Message<>();
					message.setStatus(HttpStatus.OK);
					message.setResponseMessage("Task found successfully.");
					message.setData(taskDto);
					messages.add(message);
				}
			}

			// If no valid tasks were found
			if (messages.isEmpty()) {
				Message<TaskDto> message = new Message<>();
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage(
						"No active tasks found for team '" + team + "' between " + creatingDate + " and " + endDate);
				log.warn("No active tasks found for team '{}' between {} and {}", team, creatingDate, endDate);
				messages.add(message);
			}

		} catch (Exception e) {
			// Handle any unexpected exceptions
			log.error("Failed to fetch tasks for team '{}' between {} and {}. Exception: {}", team, creatingDate,
					endDate, e.getMessage());
			Message<TaskDto> errorMessage = new Message<>();
			errorMessage.setResponseMessage("Failed to fetch tasks: " + e.getMessage());
			errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			messages.add(errorMessage);
		}

		return messages;
	}

	@Override
	public Message<TaskDto> updateStatus(UpdateTaskDto dto) {
		Message<TaskDto> message = new Message<>();
		log.info("Incoming request with task ID: {}", dto.getId());

		try {
			TaskTracking task = taskRepository.getById(dto.getId());

			if (task == null || "True".equalsIgnoreCase(task.getDeletedTag())) {
				log.error("Task not found for ID: {}", dto.getId());
				message.setResponseMessage("Task not found for ID: " + dto.getId());
				message.setStatus(HttpStatus.NOT_FOUND);
				return message;
				
			}

			String newStatus = dto.getStatus().trim(); // Trim status to avoid whitespace issues
			log.info("Trimmed status: '{}'", newStatus);

			ZoneId zoneId = ZoneId.of("Asia/Kolkata");
			LocalDateTime now = LocalDateTime.now(zoneId);
			int currentTime = now.getHour() * 100 + now.getMinute();
			Timestamp timestamp = Timestamp.valueOf(now);
			switch (newStatus) {
			case "InProgress":
				task.setStatus(newStatus);
				task.setStartworkingHours(currentTime);
				if (task.getStartingDate() == null) {
			        task.setStartingDate(timestamp);
			        log.info("Starting date set at {}", timestamp);
			    } else {
			        log.info("Starting date already present: {}", task.getStartingDate());
			    }
				task.setEndworkingHours(0); // Optional: reset end time
				log.info("Task set to InProgress at {}", currentTime);
				break;

			case "Paused":
				task.setStatus(newStatus);
				updateTimeTracking(task, currentTime);
				log.info("Task paused at {}. Updated total hours: {}", currentTime, task.getTotalHours());
				break;

			case "Completed":
				task.setStatus(newStatus);
				task.setEndDate(timestamp);
				updateTimeTracking(task, currentTime);
				log.info("Task completed at {}. Updated total hours: {}", currentTime, task.getTotalHours());
				break;

			default:
				log.warn("Unrecognized status: '{}'", newStatus);
				task.setStatus(newStatus);
				task.setStartworkingHours(currentTime);
				log.info("Task updated with status {} and start time {}", newStatus, currentTime);
				break;
			}

			log.info("Before Save: Status={}, Start={}, End={}, Total={}", task.getStatus(),
					task.getStartworkingHours(), task.getEndworkingHours(), task.getTotalHours());

			taskRepository.save(task);
			log.info("After Save: {}", taskRepository.findById(task.getId()).orElse(null));

			TaskDto taskDto = taskMapper.toTaskDto(task);
			message.setData(taskDto);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("Task status updated successfully.");
			log.info("Task status updated for ID: {}", dto.getId());

		} catch (Exception e) {
			log.error("Failed to update task status for ID: {}. Exception: {}", dto.getId(), e.getMessage(), e);
			message.setResponseMessage("Failed to update task status: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return message;
	}
	
	@Override
	public Message<RemakDto> aadRemark(RemakDto dto) {
		Message<RemakDto> message = new Message<>();
		log.info("Incoming request with task ID: {}", dto.getId());

		try {
			TaskTracking task = taskRepository.getById(dto.getId());

			if (task == null || "True".equalsIgnoreCase(task.getDeletedTag())) {
				log.error("Task not found for ID: {}", dto.getId());
				message.setResponseMessage("Task not found for ID: " + dto.getId());
				message.setStatus(HttpStatus.NOT_FOUND);
				return message;
				
			}
			
			task.setRemarks(dto.getRemark());
			taskRepository.save(task);
			TaskDto taskDto = taskMapper.toTaskDto(task);

			message.setResponseMessage("remark added sucessfully");
		    message.setStatus(HttpStatus.OK);
		    return message;
		}
		catch (Exception e) {
			log.error("Failed to update task status for ID: {}. Exception: {}", dto.getId(), e.getMessage(), e);
			message.setResponseMessage("Failed to update task status: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
		
			
	}

	

	private void updateTimeTracking(TaskTracking task, int currentTime) {
		int startTime = task.getStartworkingHours();

		int durationMinutes = calculateMinutesDiff(startTime, currentTime);

		String existingTotal = task.getTotalHours();
		int existingTotalMinutes = (existingTotal != null) ? parseHoursToMinutes(existingTotal) : 0;

		int updatedTotalMinutes = existingTotalMinutes + durationMinutes;
		String updatedFormatted = formatMinutesToHHmm(updatedTotalMinutes);

		task.setEndworkingHours(currentTime);
		task.setTotalHours(updatedFormatted);
	}

	// Helper to calculate duration between HHmm times in minutes
	// Calculates difference in minutes between two HHmm time values
	private int calculateMinutesDiff(int startTime, int endTime) {
		int startHour = startTime / 100;
		int startMinute = startTime % 100;
		int endHour = endTime / 100;
		int endMinute = endTime % 100;

		int startTotalMinutes = startHour * 60 + startMinute;
		int endTotalMinutes = endHour * 60 + endMinute;

		// Handle overnight: if end is earlier than start, add 24 hours to end
		if (endTotalMinutes < startTotalMinutes) {
			endTotalMinutes += 24 * 60;
		}

		return endTotalMinutes - startTotalMinutes;
	}

	// Converts a time string "HH:mm" to minutes
	private int parseHoursToMinutes(String time) {
		String[] parts = time.split(":");
		return Integer.parseInt(parts[0]) * 60 + Integer.parseInt(parts[1]);
	}

	// Converts total minutes to "HH:mm" format
	private String formatMinutesToHHmm(int minutes) {
		int hours = minutes / 60;
		int mins = minutes % 60;
		return String.format("%02d:%02d", hours, mins);
	}

	private int getCurrentTimeAsInt() {
		LocalTime now = LocalTime.now();
		return now.getHour() * 100 + now.getMinute();
	}

	@Override
	public TaskStatusCountDto getTaskStatusCounts(int user_id, String status) {
	    if (status == null || status.trim().isEmpty() || "null".equalsIgnoreCase(status)) {
	        // ✅ Return all statuses
	        return taskRepository.findTaskStatusCountByUserId(user_id);
	    } else {
	        // ✅ Return only count of given status
	        return taskRepository.findTaskStatusCountByUserIdAndStatus(user_id, status.trim());
	    }
	}


	@Override
	public TaskStatusCountDto getTaskStatusCountsByTeam(String team, String status) {
	    if (status == null || status.trim().isEmpty() || "null".equalsIgnoreCase(status)) {
	        // ✅ Return all statuses for the team
	        return taskRepository.getTaskStatusCountsByTeam(team);
	    } else {
	        // ✅ Return only count for the given status
	        return taskRepository.getTaskStatusCountsByTeamAndStatus(team, status.trim());
	    }
	}

//	@Override
//	public ResponseEntity<?> pauseInProgressTasks(int user_id) {
//	    List<TaskTracking> tasks = taskRepository.findByuser_idAndStatus(user_id, "InProgress");
//
//	    for (TaskTracking task : tasks) {
//	        int currentTime = getCurrentTimeAsInt(); // eg. 1425 → 2:25 PM
//	        task.setEndworkingHours(currentTime);
//	        int diff = calculateMinutesDiff(task.getStartworkingHours(), currentTime);
//	        task.setTotalHours(String.format("%d:%02d", diff / 60, diff % 60));
//	        task.setStatus("Paused");
//	    }
//
//	    taskRepository.saveAll(tasks);
//
//	    return ResponseEntity.ok("In-progress tasks paused successfully");
//	}

	public ResponseEntity<?> pauseInProgressTasks(int user_id) {
		List<TaskTracking> tasks = taskRepository.findByuser_idAndStatus(user_id, "InProgress");
		ZoneId zoneId = ZoneId.of("Asia/Kolkata");
		LocalDateTime now = LocalDateTime.now(zoneId);
		int currentTime = now.getHour() * 100 + now.getMinute();

		for (TaskTracking task : tasks) {
			task.setEndworkingHours(currentTime);// You need to update the type to LocalTime if possible

			LocalTime startTime = convertToLocalTime(task.getStartworkingHours()); // assume it's stored in HH:mm or int
			int diff = calculateMinutesDiff(task.getStartworkingHours(), currentTime);

			int hours = diff / 60;
			int minutes = diff % 60;

			task.setTotalHours(String.format("%02d:%02d", hours, minutes));
			task.setStatus("Paused");
		}

		taskRepository.saveAll(tasks);
		return ResponseEntity.ok("In-progress tasks paused successfully");
	}

	private LocalTime convertToLocalTime(int timeInt) {
		int hour = timeInt / 100;
		int minute = timeInt % 100;
		return LocalTime.of(hour, minute);
	}

	@Override
	public List<Message<TaskDto>> getAllTaskByUsername(String username) {
	    List<TaskTracking> tasks = taskRepository.getByUsername(username);
	    List<Message<TaskDto>> result = new ArrayList<>();
	    for (TaskTracking task : tasks) {
	    	if ("InProgress".equalsIgnoreCase(task.getStatus())){ // Only add tasks that are in progress)
	        TaskDto taskDto = taskMapper.toTaskDto(task);
	        Message<TaskDto> message = new Message<>();
	        message.setData(taskDto);
	        message.setResponseMessage("Task found successfully.");
	        message.setStatus(HttpStatus.OK);
	        result.add(message);
	    }
	    }
	    return result;
}

	@Override
	public Message<List<TeamTaskSummaryDto>> getTeamTaskSummary(String teamName) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
}
