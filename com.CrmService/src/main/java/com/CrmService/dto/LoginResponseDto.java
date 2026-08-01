package com.CrmService.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class LoginResponseDto {
	private String message;
	private String token;
	 private UserResponseDto data;
}
