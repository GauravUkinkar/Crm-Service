package com.CrmService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.dto.GetProjectsDto;
import com.CrmService.dto.Message;
import com.CrmService.dto.ProjectDTO;
import com.CrmService.service.ProjectService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/Admin/Project")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ProjectController {
	private final ProjectService service;

	public ProjectController(ProjectService service) {
		this.service = service;
	}
	
	@PostMapping("/addProject")
	public ResponseEntity<Message<ProjectDTO>> addClient(@RequestBody ProjectDTO dto) {
		log.info("In ClientController addClient() with request: {}", dto);
		Message<ProjectDTO> message = service.addProject(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	
	@PostMapping("/updateProject")
	public ResponseEntity<Message<ProjectDTO>> updateClient(@RequestBody ProjectDTO dto) {
		log.info("In ClientController addClient() with request: {}", dto);
		Message<ProjectDTO> message = service.updateProject(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	
	@DeleteMapping("/dleteProject")
	public ResponseEntity<Message<ProjectDTO>> deleteClient(@RequestParam("ProjectId")int id) {
		log.info("In ClientController addClient() with request: {}", id);
		Message<ProjectDTO> message = service.deleteProject(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@PostMapping("/getProjectByClientName")
    public ResponseEntity<Message<List<ProjectDTO>>> getProjectsByClientId(@RequestBody GetProjectsDto request) {
        Message<List<ProjectDTO>> response = service.getProjectsByClientName(request);
        
        // Checking the status to determine the response code
        if (response.getStatus() == HttpStatus.OK) {
            return ResponseEntity.ok(response);  // Return 200 OK with data
        } else {
            return ResponseEntity.status(response.getStatus()).body(response);  // Return error status with message
        }
    }
	
	@GetMapping("/getProject")
	public ResponseEntity<Message<ProjectDTO>> getClient(@RequestParam("name") String name) {
		log.info("In ClientController addClient() with request: {}", name);
		Message<ProjectDTO> message = service.getProject(name);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	 @GetMapping("/getAllProjects")
	    public List<ProjectDTO> getAllProjects() {
	        return service.getAllProjects();
	    }

}
