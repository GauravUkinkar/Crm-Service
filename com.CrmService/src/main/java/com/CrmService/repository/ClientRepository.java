package com.CrmService.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.CrmService.model.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

	Client getByName(String name);

}
