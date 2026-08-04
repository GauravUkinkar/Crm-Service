package com.CrmService.controller;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.config.JwtUtil;
import com.CrmService.dto.Message;
import com.CrmService.dto.TaskSchedulerDto;
import com.CrmService.dto.TeamDto;
import com.CrmService.model.TaskScheduler;
import com.CrmService.model.TaskStatus;
import com.CrmService.model.TaskTracking;
import com.CrmService.repository.TaskRepository;
import com.CrmService.repository.TaskSchedulerRepository;
import com.CrmService.serviceImpl.TaskSchedulerServiceImpl;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskShedularController {
	
	private final TaskSchedulerRepository taskShedularService;
	private final TaskRepository taskRepo;
	private final TaskSchedulerServiceImpl service;
	private final JwtUtil jwtUtil;
	
	@PostMapping("/heartbeat")
	public ResponseEntity<?> heartbeat(HttpServletRequest request) {
		
		  Date today = new Date();

		  String token = null;
		  
		  if (request.getCookies()!= null) {
			  for(Cookie cookie : request.getCookies()) {
				  if ("token".equals(cookie.getName())) {
					  token = cookie.getValue();
					  break;
					
				}
			  }
		  }
		  
		  int uid = jwtUtil.extractUserId(token);
		    TaskScheduler taskSchedular = taskShedularService.findByUserIdAndDate(uid, today);
	  

	    TaskTracking tasks =
                taskRepo.getById(taskSchedular.getTaskId());
	    
//	    TaskTracking tasks = taskRepo.getById(uid);


	    if (taskSchedular != null) {

	    	taskSchedular.setLast_seen(LocalTime.now());   
	    	taskSchedular.setStatus(TaskStatus.ONLINE);
	        

	        if (tasks != null) {
	        	tasks.setStatus("InProgress");
            }

	        taskShedularService.save(taskSchedular);
	        taskRepo.save(tasks);
	    }

	    return ResponseEntity.ok("Heartbeat received");
	}
	
	@PostMapping("/addTaskSchedular")
	public ResponseEntity<Message<TaskSchedulerDto>> addTeam(@RequestBody TaskSchedulerDto dto) {
		Message<TaskSchedulerDto> message = service.addTaskSchedular(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
}
