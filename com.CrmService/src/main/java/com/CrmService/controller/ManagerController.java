package com.CrmService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.dto.GetProjectsDto;
import com.CrmService.dto.Message;
import com.CrmService.dto.ProjectDTO;
import com.CrmService.dto.TeamDto;
import com.CrmService.service.ProjectService;
import com.CrmService.service.TeamService;
import com.CrmService.serviceImpl.ClientServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequestMapping
@RestController
@RequiredArgsConstructor
@CrossOrigin("/managerController")
public class ManagerController {	
	private final TeamService service;
	private final ProjectService projectservice;
	
// Team
	@PostMapping("/addTeam")
	public ResponseEntity<Message<TeamDto>> addTeam(@RequestBody TeamDto dto) {
		log.info("In TeamController addTeam() with request: {}", dto);
		Message<TeamDto> message = service.addTeam(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

	@PutMapping("/updateTeam")
	public ResponseEntity<Message<TeamDto>> updateTeam(@RequestBody TeamDto dto) {
		log.info("In ClientController updateTeam() with request: {}", dto);
		Message<TeamDto> message = service.updateTeam(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

	@GetMapping("/getAllTeams")
	public ResponseEntity<List<Message<TeamDto>>> getAllTeam() {
		log.info("In ClientController getAllTeam()");
		List<Message<TeamDto>> messages = service.getAllTeam();
		HttpStatus httpStatus = messages.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK;
		return ResponseEntity.status(httpStatus).body(messages);
	}

	@GetMapping("/getTeamByName")
	public ResponseEntity<Message<TeamDto>> getTeamByName(@RequestParam("TeamName") String name) {
		log.info("In ClientController getTeamByName()" + name);
		Message<TeamDto> message = service.GetTeamByName(name);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);

	}
	@GetMapping("/getTeamByMangerName")
	public ResponseEntity<Message<TeamDto>> getTeamByManegerName(@RequestParam("MangereName") String manegerName) {
		log.info("In ClientController getTeamByName()" + manegerName);
		Message<TeamDto> message = service.GetTeamByManagerName(manegerName);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);

	}

	@DeleteMapping("/dleteTeam")
	public ResponseEntity<Message<TeamDto>> deleteTeamClient(@RequestParam("TeamId") int id) {
		Message<TeamDto> message = service.deleteTeam(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	
// Project	
	@PostMapping("/addProject")
	public ResponseEntity<Message<ProjectDTO>> addClient(@RequestBody ProjectDTO dto) {
		log.info("In ClientController addClient() with request: {}", dto);
		Message<ProjectDTO> message = projectservice.addProject(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	
	@PostMapping("/updateProject")
	public ResponseEntity<Message<ProjectDTO>> updateClient(@RequestBody ProjectDTO dto) {
		log.info("In ClientController addClient() with request: {}", dto);
		Message<ProjectDTO> message = projectservice.updateProject(dto);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	
	@DeleteMapping("/dleteProject")
	public ResponseEntity<Message<ProjectDTO>> deleteClient(@RequestParam("ProjectId")int id) {
		log.info("In ClientController addClient() with request: {}", id);
		Message<ProjectDTO> message = projectservice.deleteProject(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@PostMapping("/getProjectByClientName")
    public ResponseEntity<Message<List<ProjectDTO>>> getProjectsByClientId(@RequestBody GetProjectsDto request) {
        Message<List<ProjectDTO>> response = projectservice.getProjectsByClientName(request);
        
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
		Message<ProjectDTO> message = projectservice.getProject(name);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	 @GetMapping("/getAllProjects")
	    public List<ProjectDTO> getAllProjects() {
	        return projectservice.getAllProjects();
	    }

}
