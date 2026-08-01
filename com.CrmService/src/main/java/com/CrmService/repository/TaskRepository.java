package com.CrmService.repository;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CrmService.dto.TaskStatusCountDto;
import com.CrmService.dto.TeamTaskSummaryDto;
import com.CrmService.model.TaskTracking;

@Repository
public interface TaskRepository extends JpaRepository<TaskTracking, Integer> {



	List<TaskTracking> getByProject(String project);
	List<TaskTracking> findByUserIdAndStatus(int userId, String status);
	List<TaskTracking> getByTeam(String team);

	List<TaskTracking> getByUsername(String username);
	
    List<TaskTracking> findByTeamAndCreatingDateBetween(String team, String startDate, String endDate);

	List<TaskTracking> findByDeletedTagFalse();
	
	 
	 @Query("SELECT new com.Pandoza_Admin.Dto.TaskStatusCountDto(" +
		       "SUM(CASE WHEN t.status = 'Completed' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'InProgress' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'Paused' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'Delayed' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'Pending' THEN 1 ELSE 0 END)) " +
		       "FROM TaskTracking t " +
		       "WHERE t.team = :team AND t.deletedTag = 'false'")
		TaskStatusCountDto getTaskStatusCountsByTeam(@Param("team") String team);
	 
	 List<TaskTracking> findByuser_idAndStatus(int user_id, String status);

	List<TaskTracking> getByuser_id(int user_id);
	@Query("SELECT new com.Pandoza_Admin.Dto.TaskStatusCountDto(" +
		       "t.user.id, " +
		       "SUM(CASE WHEN t.status = 'Completed' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'InProgress' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'Paused' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'Delayed' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'Pending' THEN 1 ELSE 0 END)) " +
		       "FROM TaskTracking t " +
		       "WHERE t.user.id = :userId AND t.deletedTag = 'false' " +
		       "GROUP BY t.user.id")
	TaskStatusCountDto findTaskStatusCountByUserId(@Param("userId")int userId);
	
	List<TaskTracking> findByUsernameAndStatus(String username, String status);
	// using in taskSchedular
	@Query("""
		    SELECT t
		    FROM TaskTracking t
		    WHERE t.user.id = :userId
		      AND (t.status = 'InProgress')
		""")
		TaskTracking getInProgressOrPausedTask(@Param("userId") int userId);
	
	Page<TaskTracking> findByStatusAndDeletedTagNotIgnoreCase(String trim, String string, Pageable pageable);
	Page<TaskTracking> findByTeamAndStatusAndDeletedTagNotIgnoreCase(String team, String trim, String string,
			Pageable pageable);
	Page<TaskTracking> findByUser_IdAndStatusAndDeletedTagNotIgnoreCase(int user_id, String trim, String string,
			Pageable pageable);
	Page<TaskTracking> findByUser_IdAndDeletedTagNotIgnoreCase(int user_id, String string, Pageable pageable);
	TaskStatusCountDto findTaskStatusCountByUserIdAndStatus(int user_id, String trim);
	TaskStatusCountDto getTaskStatusCountsByTeamAndStatus(String team, String trim);
	Page<TaskTracking> findByTeamAndDeletedTagNotIgnoreCase(String team, String string, Pageable pageable);
	Page<TaskTracking> findByDeletedTagNotIgnoreCase(String string, Pageable pageable);
	
	
	@Query("SELECT new com.Pandoza_Admin.Dto.TeamTaskSummaryDto( " +
		       "t.team, " +
		       "SUM(CASE WHEN DATE(t.createdDate) = CURRENT_DATE THEN 1 ELSE 0 END), " +
		       "COUNT(t), " +
		       "SUM(CASE WHEN t.status = 'COMPLETED' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'IN_PROGRESS' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'DELAYED' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'PAUSED' THEN 1 ELSE 0 END), " +
		       "SUM(CASE WHEN t.status = 'PENDING' THEN 1 ELSE 0 END)) " +
		       "FROM TaskTracking t " +
		       "WHERE (:teamName IS NULL OR t.team = :teamName) " +
		       "GROUP BY t.team")
		List<TeamTaskSummaryDto> getTeamTaskSummary(@Param("teamName") String teamName);
	}
