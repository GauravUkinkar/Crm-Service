package com.CrmService.repository;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CrmService.model.TaskScheduler;

@Repository
public interface TaskSchedulerRepository extends JpaRepository<TaskScheduler , Integer>{
	
	TaskScheduler findByUserIdAndDate(int userId, Date date);


	List<TaskScheduler> findByDate(Date today);
}
