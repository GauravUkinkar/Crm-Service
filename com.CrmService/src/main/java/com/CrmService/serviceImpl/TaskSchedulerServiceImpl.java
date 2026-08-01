package com.CrmService.serviceImpl;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.CrmService.model.TaskScheduler;
import com.CrmService.model.TaskStatus;
import com.CrmService.model.TaskTracking;
import com.CrmService.repository.TaskRepository;
import com.CrmService.repository.TaskSchedulerRepository;
import com.CrmService.service.TaskSchedulerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskSchedulerServiceImpl implements TaskSchedulerService {

	private final TaskSchedulerRepository taskRepository;
	private final TaskRepository taskRepo;

	@Override
	@Scheduled(fixedRate = 30000)
	public void getSchedularById() {

        try {

            Date today = new Date();

            // Fetch only today's scheduler records
            List<TaskScheduler> users = taskRepository.findByDate(today);

            LocalTime currentTime = LocalTime.now();

            for (TaskScheduler user : users) {
            	
                 
                int userId = user.getUserId();

                // Fetch today's tasks of the user
          
                TaskTracking tasks =
                        taskRepo.getInProgressOrPausedTask(userId);

                LocalTime lastSeen = user.getLast_seen();

                long seconds =
                        Duration.between(lastSeen, currentTime).getSeconds();
                System.out.println(seconds);

                // User is OFFLINE
                if (seconds >= 30) { 

                    user.setStatus(TaskStatus.OFFLINE);
                	   tasks.setStatus("Paused");
                	      user.setTaskId(tasks.getId());
                    }

                
                // Save user status
                taskRepository.save(user);

                // Save task status
                taskRepo.save(tasks);
                System.out.println(tasks);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
