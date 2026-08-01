package com.CrmService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginRequest {
	  private String username;
	    private String password;
}
