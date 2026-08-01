package com.CrmService.controller;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.model.TaskScheduler;
import com.CrmService.model.TaskStatus;
import com.CrmService.model.TaskTracking;
import com.CrmService.repository.TaskRepository;
import com.CrmService.repository.TaskSchedulerRepository;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping
@AllArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TaskShedularController {
	
	private final TaskSchedulerRepository taskShedularService;
	private final TaskRepository taskRepo;
	
	@PostMapping("/heartbeat")
	public ResponseEntity<?> heartbeat(@RequestParam int userId) {
		
		  Date today = new Date();

	    TaskScheduler user = taskShedularService.findByUserIdAndDate(userId, today);
	    
	    TaskTracking tasks =
                taskRepo.getById(user.getTaskId());

	    if (user != null) {

	        user.setLast_seen(LocalTime.now());
	        user.setStatus(TaskStatus.ONLINE);
	        

	        if (tasks != null) {
	        	tasks.setStatus("InProgress");
            }

	        taskShedularService.save(user);
	        taskRepo.save(tasks);
	    }

	    return ResponseEntity.ok("Heartbeat received");
	}
}
