package com.CrmService.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class UserResponseDto {
	  private int id;
	  private String employeeId;
	    private String username;
	    private String password;
	    private RoleDto role;
	    private TeamDto team;
		private String employeeName;

}
