package com.CrmService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CrmService.model.Team;



@Repository
public interface TeamRepository extends JpaRepository<Team, Integer> {

	Team getByName(String name);

	Team getByManegerName(String manegerName);

}
