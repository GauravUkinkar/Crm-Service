package com.CrmService.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import com.CrmService.model.Project;

import jakarta.transaction.Transactional;

@Repository
public interface ProjectsRepository extends JpaRepository<Project, Integer> {

	Project getByName(String name);


	List<Project> findByClientName(String clientName);
	 @Transactional
	 @Modifying
	    void deleteAllByClientName(String clientName);


	List<Project> findAllByClientName(String clientName);


	List<Project> findByNameIn(List<String> projectNames);


}
