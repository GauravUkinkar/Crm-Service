package com.CrmService.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.dto.ChangePasswordDto;
import com.CrmService.dto.Message;
import com.CrmService.dto.RegisterUserDto;
import com.CrmService.dto.UserResponseDto;
import com.CrmService.serviceImpl.UserServiceImpl;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/Admin")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController{
	private final UserServiceImpl userServiceImpl;


	public UserController(UserServiceImpl userServiceImpl) {
		super();
		this.userServiceImpl = userServiceImpl;
	}
	@PostMapping("/register")
	public ResponseEntity<Message<RegisterUserDto>> registerUser(@RequestBody RegisterUserDto user) {
		log.info("In UserController registerUser() with request: {}", user);
        Message<RegisterUserDto> message = userServiceImpl.register(user);
        HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
        return ResponseEntity.status(httpStatus).body(message);
	}
	@PostMapping("/updateUser")
	public ResponseEntity<Message<RegisterUserDto>> UpdateUser(@RequestBody RegisterUserDto user) {
		log.info("In UserController registerUser() with request: {}", user);
        Message<RegisterUserDto> message = userServiceImpl.updateUser(user);
        HttpStatus httpStatus = HttpStatus.valueOf(message.getStatus().value());
        return ResponseEntity.status(httpStatus).body(message);
	}
	@GetMapping("/{username}")
    public ResponseEntity<Message<UserResponseDto>> getUserByUsername(@PathVariable String username) {
        Message<UserResponseDto> response = userServiceImpl.getUserByUsername(username);
        return new ResponseEntity<>(response, response.getStatus());
    }
	@GetMapping("/GetUserById")
	public ResponseEntity<Message<UserResponseDto>> getUserById(@RequestParam("UserId") int id) {
	    Message<UserResponseDto> response = userServiceImpl.getUserById(id);
	    return new ResponseEntity<>(response, response.getStatus());
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<Message<List<UserResponseDto>>> getAllUsers() {
	    try {
	        Message<List<UserResponseDto>> message = userServiceImpl.getAllUsers();
	        return ResponseEntity.status(message.getStatus()).body(message);
	    } catch (Exception e) {
	        log.error("Exception in getAllUsers: ", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(new Message<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch users: " + e.getMessage(), null));
	    }
	}
	@PostMapping("/ChangePassword")
	public ResponseEntity<Message<UserResponseDto>> changePassword(@RequestBody ChangePasswordDto dto) {
		System.out.println(dto);
	    Message<UserResponseDto> response = userServiceImpl.changePassword(dto);
	    return new ResponseEntity<>(response, response.getStatus());
	}
	@DeleteMapping("/deleteUser")
	public ResponseEntity<Message<UserResponseDto>> deleteUser(@RequestParam("UserId") int id) {
	    Message<UserResponseDto> response = userServiceImpl.deleteUser(id);
	    return new ResponseEntity<>(response, response.getStatus());
	}

	@GetMapping("/GetByTeamName")
	public ResponseEntity<Message<List<UserResponseDto>>> getAllUsersByTeamName(@RequestParam("TeamName") String teamName) {
		try {
	        Message<List<UserResponseDto>> message = userServiceImpl.getallUserSByTeamName(teamName);
	        return ResponseEntity.status(message.getStatus()).body(message);
	    } catch (Exception e) {
	        log.error("Exception in getAllUsers: ", e);
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
	            .body(new Message<>(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to fetch users: " + e.getMessage(), null));
	    }
	}


}
