package com.CrmService.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class RegisterUserDto {
	private int id;
	private String username;
	private String password;
	private int roleid;
	private Integer  teamid;
	private String employeeId;
	private String employeeName;


}
