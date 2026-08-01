package com.CrmService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.CrmService.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUsername(String username);
	  User getByUsername(String username);

	      @Query("SELECT u FROM User u WHERE u.team.name = :teamName")
	      List<User> findByTeamName(@Param("teamName") String teamName);



}
