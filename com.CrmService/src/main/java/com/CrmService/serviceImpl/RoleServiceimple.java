package com.CrmService.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.CrmService.model.Role;
import com.CrmService.repository.RoleRepository;
import com.CrmService.service.RoleService;

@Service
public class RoleServiceimple implements RoleService {
@Autowired
RoleRepository roleRepository;
	@Override
	public List<Role> getAllRoles() {
		 return roleRepository.findAll();
	}

}
