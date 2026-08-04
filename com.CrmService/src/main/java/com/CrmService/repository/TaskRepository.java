package com.CrmService.repository;

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

	List<TaskTracking> findByuIdAndStatus(int uId, String status);

	List<TaskTracking> getByTeam(String team);

	List<TaskTracking> getByUsername(String username);

	List<TaskTracking> findByTeamAndCreatingDateBetween(String team, String startDate, String endDate);

	List<TaskTracking> findByDeletedTagFalse();

	@Query("""
			SELECT new com.CrmService.dto.TaskStatusCountDto(
			    SUM(CASE WHEN t.status = 'Completed' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'InProgress' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'Paused' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'Delayed' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'Pending' THEN 1 ELSE 0 END)
			)
			FROM TaskTracking t
			WHERE t.team = :team
			AND t.deletedTag = 'false'
			""")
	TaskStatusCountDto getTaskStatusCountsByTeam(@Param("team") String team);

	List<TaskTracking> getByuId(int uId);

	@Query("""
			SELECT new com.CrmService.dto.TaskStatusCountDto(
			    t.uId,
			    SUM(CASE WHEN t.status = 'Completed' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'InProgress' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'Paused' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'Delayed' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'Pending' THEN 1 ELSE 0 END)
			)
			FROM TaskTracking t
			WHERE t.uId = :uId
			AND t.deletedTag = 'false'
			GROUP BY t.uId
			""")
	TaskStatusCountDto findTaskStatusCountByuId(@Param("uId") int uId);

	List<TaskTracking> findByUsernameAndStatus(String username, String status);

	@Query("""
			SELECT t
			FROM TaskTracking t
			WHERE t.uId = :uId
			AND t.status = 'InProgress'
			""")
	TaskTracking getInProgressOrPausedTask(@Param("uId") int uId);

	Page<TaskTracking> findByStatusAndDeletedTagNotIgnoreCase(String status, String deletedTag, Pageable pageable);

	Page<TaskTracking> findByTeamAndStatusAndDeletedTagNotIgnoreCase(String team, String status, String deletedTag,
			Pageable pageable);

	Page<TaskTracking> findByUIdAndStatusAndDeletedTagNotIgnoreCase(int uId, String status, String deletedTag,
			Pageable pageable);

	Page<TaskTracking> findByUIdAndDeletedTagNotIgnoreCase(int uId, String deletedTag, Pageable pageable);

	Page<TaskTracking> findByTeamAndDeletedTagNotIgnoreCase(String team, String deletedTag, Pageable pageable);

	Page<TaskTracking> findByDeletedTagNotIgnoreCase(String deletedTag, Pageable pageable);

	@Query("""
			SELECT new com.CrmService.dto.TeamTaskSummaryDto(
			    t.team,
			    SUM(CASE WHEN DATE(t.createdDate) = CURRENT_DATE THEN 1 ELSE 0 END),
			    COUNT(t),
			    SUM(CASE WHEN t.status = 'COMPLETED' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'IN_PROGRESS' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'DELAYED' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'PAUSED' THEN 1 ELSE 0 END),
			    SUM(CASE WHEN t.status = 'PENDING' THEN 1 ELSE 0 END)
			)
			FROM TaskTracking t
			WHERE (:teamName IS NULL OR t.team = :teamName)
			GROUP BY t.team
			""")
	List<TeamTaskSummaryDto> getTeamTaskSummary(@Param("teamName") String teamName);
	
	
	@Query("""
		    SELECT new com.CrmService.dto.TaskStatusCountDto(
		        SUM(CASE WHEN t.status = 'Completed' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'InProgress' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'Paused' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'Delayed' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'Pending' AND t.status = :status THEN 1 ELSE 0 END)
		    )
		    FROM TaskTracking t
		    WHERE t.uId = :uId
		      AND t.deletedTag = 'false'
		    """)
		TaskStatusCountDto findTaskStatusCountByUserIdAndStatus(
		        @Param("uId") int uId,
		        @Param("status") String status);

	
	@Query("""
		    SELECT new com.CrmService.dto.TaskStatusCountDto(
		        SUM(CASE WHEN t.status = 'Completed' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'InProgress' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'Paused' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'Delayed' AND t.status = :status THEN 1 ELSE 0 END),
		        SUM(CASE WHEN t.status = 'Pending' AND t.status = :status THEN 1 ELSE 0 END)
		    )
		    FROM TaskTracking t
		    WHERE t.team = :team
		      AND t.deletedTag = 'false'
		    """)
		TaskStatusCountDto getTaskStatusCountsByTeamAndStatus(
		        @Param("team") String team,
		        @Param("status") String status);
}