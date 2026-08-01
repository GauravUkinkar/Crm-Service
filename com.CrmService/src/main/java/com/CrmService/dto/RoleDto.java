package com.CrmService.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ToString
public class RoleDto {
	 private int id;
	    private String name;
}
