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
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.dto.ClientDTO;
import com.CrmService.dto.Message;
import com.CrmService.serviceImpl.ClientServiceImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/Admin/client")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ClientController {
	public final ClientServiceImpl clientServiceImpl;

	public ClientController(ClientServiceImpl clientServiceImpl) {
		this.clientServiceImpl = clientServiceImpl;
	}

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
}
