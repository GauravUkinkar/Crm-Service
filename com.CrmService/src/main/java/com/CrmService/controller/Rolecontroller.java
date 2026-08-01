package com.CrmService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.model.Role;
import com.CrmService.service.RoleService;

@RestController
@RequestMapping("/Role")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class Rolecontroller {
	 @Autowired
	    private RoleService roleService;

	    @GetMapping
	    public List<Role> getAllRoles() {
	        return roleService.getAllRoles(); // Calls the service to get all roles
	    }
}
