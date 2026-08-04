package com.CrmService.serviceImpl;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.CrmService.dto.Message;
import com.CrmService.dto.TaskSchedulerDto;
import com.CrmService.mapperImpl.TaskSchedulerMapperImpl;
import com.CrmService.model.TaskScheduler;
import com.CrmService.model.TaskStatus;
import com.CrmService.model.TaskTracking;
import com.CrmService.repository.TaskRepository;
import com.CrmService.repository.TaskSchedulerRepository;
import com.CrmService.service.TaskSchedulerService;
import com.CrmService.util.Constants;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TaskSchedulerServiceImpl implements TaskSchedulerService {


	private final TaskSchedulerRepository taskRepository;
	private final TaskRepository taskRepo;
	private final TaskSchedulerMapperImpl taskShedularmap;

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

	@Override
	public Message<TaskSchedulerDto> addTaskSchedular(TaskSchedulerDto request) {
		Message<TaskSchedulerDto> response = new Message<>();
		
	    try {
	    	if (request == null) {
	    		response.setStatus(HttpStatus.BAD_REQUEST);
				response.setResponseMessage(Constants.INVALID_DATA);
				return response;
			}
	    	
	    	TaskScheduler taskSchedule = taskShedularmap.toTaskScheduler(request);
	    	taskRepository.save(taskSchedule);
	    	TaskSchedulerDto dto = taskShedularmap.toTaskSchedulerDto(taskSchedule);
	    	
	    	response.setStatus(HttpStatus.OK);
	    	response.setResponseMessage(Constants.TASKSCHEDULAR_ADDED_SUCCESFULLY);
	    	return response;
			
		} catch (Exception e) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			response.setResponseMessage(e.getMessage());
			return response;
		}
	}


}
