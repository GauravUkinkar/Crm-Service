package com.CrmService.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.config.JwtUtil;
import com.CrmService.dto.Message;
import com.CrmService.dto.RemakDto;
import com.CrmService.dto.TaskDto;
import com.CrmService.dto.TaskStatusCountDto;
import com.CrmService.dto.TeamDto;
import com.CrmService.dto.TeamTaskSummaryDto;
import com.CrmService.dto.UpdateTaskDto;
import com.CrmService.service.TaskService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/AuthController")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AuthController {
	private final TaskService taskService;
    private final JwtUtil jwtutil;
    
    
    
	@PutMapping("/addRemark")
	public ResponseEntity<Message<RemakDto>> addremark(@RequestBody RemakDto remarkDto){
		Message<RemakDto> response = taskService.aadRemark(remarkDto);
		
		return new ResponseEntity<>(response ,response.getStatus());
	}
	
	
	@GetMapping("/task-summary")
	public ResponseEntity<Message<List<TeamTaskSummaryDto>>> getTaskSummary(
	        @RequestParam(value = "team", required = false) String team) {
	    Message<List<TeamTaskSummaryDto>> response = taskService.getTeamTaskSummary(team);
	    return new ResponseEntity<>(response, response.getStatus());
	}
	
	@PutMapping("/pause/{user_id}")
	public ResponseEntity<?> pauseTasks(@PathVariable int user_id) {
        return taskService.pauseInProgressTasks(user_id);
    }
      
	@GetMapping("/status-count/{user_id}")
	public ResponseEntity<TaskStatusCountDto> getStatusCount(
	        @PathVariable int user_id,
	        @RequestParam(value = "status", required = false) String status) {

	    TaskStatusCountDto dto = taskService.getTaskStatusCounts(user_id, status);
	    return ResponseEntity.ok(dto);
	}

	@GetMapping("/status-count/team/{team}")
	public ResponseEntity<TaskStatusCountDto> getStatusCountsByTeam(
	        @PathVariable String team,
	        @RequestParam(value = "status", required = false) String status) {

	    TaskStatusCountDto dto = taskService.getTaskStatusCountsByTeam(team, status);
	    return ResponseEntity.ok(dto);
	}

	@PostMapping("/addTask")
	public ResponseEntity<Message<TaskDto>> addTask(@RequestBody TaskDto taskDto , HttpServletRequest request) {
		String token = null;
		for(Cookie cookie : request.getCookies()) {
			  if ("token".equals(cookie.getName())) {
				  token = cookie.getValue();
				  break;
				
			}
		  }
		int userId = jwtutil.extractUserId(token);
		taskDto.setUId(userId);
		Message<TaskDto> response = taskService.addTask(taskDto);
        
		return new ResponseEntity<>(response, response.getStatus());
	}

	@PutMapping("/updateTask")
	public ResponseEntity<Message<TaskDto>> updateTask(@RequestBody TaskDto taskDto) {
		Message<TaskDto> response = taskService.updateTask(taskDto);

		return new ResponseEntity<>(response, response.getStatus());
	}
	@PutMapping("/updateStatus")
	public ResponseEntity<Message<TaskDto>> updateStatus(@RequestBody UpdateTaskDto dto) {
		Message<TaskDto> response = taskService.updateStatus(dto);

		return new ResponseEntity<>(response, response.getStatus());
	}

	@DeleteMapping("/deleteTask")
	public ResponseEntity<Message<TaskDto>> deleteTask(@RequestParam("TaskId") int id) {
		Message<TaskDto> response = taskService.deleteTask(id);

		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/getTaskById")
	public ResponseEntity<Message<TaskDto>> getTask(@RequestParam("TaskId") int id) {
		Message<TaskDto> response = taskService.getTask(id);

		return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/allTask")
	public ResponseEntity<Message<Map<String, Object>>> getAllTasks(
	        @RequestParam(required = false) String status,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size
	) {
	    Message<Map<String, Object>> response = taskService.getAllTask(status, page, size);
	    return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/getTaskByproject")
	public ResponseEntity<List<Message<TaskDto>>> getTasksByProject(@RequestParam("projectName") String project) {
		List<Message<TaskDto>> response = taskService.getTaskByProjectId(project);

		if (response != null && !response.isEmpty() && response.get(0).getStatus() == HttpStatus.NOT_FOUND) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/getTaskByTeam")
	public ResponseEntity<Message<Map<String, Object>>> getTasksByTeam(
	        @RequestParam("TeamName") String team,
	        @RequestParam(value = "status", required = false) String status,
	        @RequestParam(defaultValue = "0") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Message<Map<String, Object>> response = taskService.getTaskByTeam(team, status, page, size);
	    return ResponseEntity.status(response.getStatus()).body(response);
	}

	@GetMapping("/getTaskByUserName")
	public ResponseEntity<List<Message<TaskDto>>> getTasksByusername(@RequestParam("UserName") String username) {
		List<Message<TaskDto>> response = taskService.getTaskByusername(username);

		if (response != null && !response.isEmpty() && response.get(0).getStatus() == HttpStatus.NOT_FOUND) {
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/getTaskByUserId")
	public ResponseEntity<Message<Map<String, Object>>> getTaskByUserId(
	        @RequestParam("userId") int userId,
	        @RequestParam(value = "status", required = false) String status,
	        @RequestParam(defaultValue = "1") int page,
	        @RequestParam(defaultValue = "10") int size) {

	    Message<Map<String, Object>> response = taskService.getTaskByUserId(userId, status, page, size);
	    return ResponseEntity.status(response.getStatus()).body(response);
	}


	 @GetMapping("/by-date")
	 public ResponseEntity<List<Message<TaskDto>>> getTasksByDateRange(
	            @RequestParam("team") String team,
	            @RequestParam("startDate") String startDate,
	            @RequestParam("endDate") String endDate) {

	        List<Message<TaskDto>> tasks = taskService.getTasksByTeamAndDateRange(team, startDate, endDate);
	        return ResponseEntity.ok(tasks);
	    }
	 @GetMapping("getTasksByUsernameandStatus/{username}")
	    public ResponseEntity<List<Message<TaskDto>>> getTasksByUsernameandStatus(@PathVariable String username) {
		 List<Message<TaskDto>> tasks =taskService.getAllTaskByUsername(username);
	        return ResponseEntity.ok(tasks);
	    }
	    


}
