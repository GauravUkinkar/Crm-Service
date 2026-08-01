package com.CrmService.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.CrmService.dto.LoginRequest;
import com.CrmService.dto.LoginResponseDto;
import com.CrmService.dto.Message;
import com.CrmService.serviceImpl.UserServiceImpl;

import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/AuthController")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Validated
public class AuthController {
	
	private final UserServiceImpl userServiceImpl;


	public AuthController(UserServiceImpl userServiceImpl) {
		super();
		this.userServiceImpl = userServiceImpl;
	}


	@PostMapping("/login")
    public ResponseEntity<Message<LoginResponseDto>> userLogin(@Valid @RequestBody LoginRequest request) {
        log.info("In UserRegistrationController userLogin() with request: {}", request);
        System.out.println(request);	
        Message<LoginResponseDto> response = userServiceImpl.userLogin(request);
        return new ResponseEntity<>(response, response.getStatus());
    }
	
}
