package com.CrmService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.dto.CategoryDto;
import com.CrmService.dto.ClientDTO;
import com.CrmService.dto.Message;
import com.CrmService.dto.TeamDto;
import com.CrmService.service.CategoryService;
import com.CrmService.service.TeamService;
import com.CrmService.serviceImpl.ClientServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/adminController")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
public class AdminController {
	private final CategoryService categoryservice;
	public final ClientServiceImpl clientServiceImpl;
	private final TeamService service;
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
	public ResponseEntity<Message<TeamDto>> deleteteamClient(@RequestParam("TeamId") int id) {
		Message<TeamDto> message = service.deleteTeam(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

	// Client
	
	@PostMapping("/addClient")
	public ResponseEntity<Message<ClientDTO>> addClient(@RequestBody ClientDTO clientDTO) {
		log.info("In ClientController addClient() with request: {}", clientDTO);
		Message<ClientDTO> message = clientServiceImpl.addClient(clientDTO);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

	@PutMapping("/updateClient")
	public ResponseEntity<Message<ClientDTO>> updateClient(@RequestBody ClientDTO clientDTO) {
		log.info("In ClientController addClient() with request: {}", clientDTO);
		Message<ClientDTO> message = clientServiceImpl.updateClient(clientDTO);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}

	@GetMapping("/getAllClients")
	public List<ClientDTO> getAllClients() {
		return clientServiceImpl.getAllClients();
	}

	@GetMapping("/getClient/{id}")
	public ResponseEntity<Message<ClientDTO>> getClient(@PathVariable int id) {
		Message<ClientDTO> message = clientServiceImpl.getClientById(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@DeleteMapping("/dleteClient/{id}")
	public ResponseEntity<Message<ClientDTO>> deleteClient(@PathVariable int id) {
		Message<ClientDTO> message = clientServiceImpl.deleteClient(id);
		HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	
	@PostMapping("/addCategory")
	public ResponseEntity<Message<CategoryDto>>AddCategory(@RequestBody CategoryDto request) {
		log.info("In usercontroller login() with request:{}",request);
		Message<CategoryDto> message=categoryservice.AddCategory(request);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@PutMapping("/updateCategory")
	public ResponseEntity<Message<CategoryDto>>UpdateCategory(@RequestBody CategoryDto request) {
		log.info("In usercontroller login() with request:{}",request);
		Message<CategoryDto> message=categoryservice.UpdateCategory(request);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
   }
	@DeleteMapping("/deleteCategory/{cId}")
	public ResponseEntity<Message<CategoryDto>>DeleteCategory(@PathVariable int cId) {
		log.info("In usercontroller login() with request:{}",cId);
		Message<CategoryDto> message=categoryservice.DeleteCategory(cId);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
   }
	@GetMapping("/getCategoryById/{cId}")
	public ResponseEntity<Message<CategoryDto>>GetCategoryById(@PathVariable int cId) {
		log.info("In usercontroller login() with request:{}",cId);
		Message<CategoryDto> message=categoryservice.GetCategoryById(cId);
		HttpStatus httpStatus=HttpStatus.valueOf(message.getStatus().value());
		return ResponseEntity.status(httpStatus).body(message);
	}
	@GetMapping("/GetAllCategories")
	public ResponseEntity<List<Message<CategoryDto>>>GetAllCategories() {
		List<Message<CategoryDto>> message=categoryservice.GetAllCategories();
		return ResponseEntity.status(HttpStatus.OK).body(message);
	}


}
