package com.CrmService.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChangePasswordDto {

	
	private String username;

	private String confirmPassword;

	private String newPassword;
}
