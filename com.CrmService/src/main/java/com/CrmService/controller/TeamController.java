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

import com.CrmService.dto.Message;
import com.CrmService.dto.TeamDto;
import com.CrmService.service.TeamService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/Admin/Team")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class TeamController {
	private final TeamService service;

	public TeamController(TeamService service) {
		super();
		this.service = service;
	}

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
	public ResponseEntity<Message<TeamDto>> deleteClient(@RequestParam("TeamId") int id) {
		Message<TeamDto> message = service.deleteTeam(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

}
