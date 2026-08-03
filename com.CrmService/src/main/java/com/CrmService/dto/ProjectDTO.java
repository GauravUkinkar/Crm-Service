package com.CrmService.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class ProjectDTO {
	 private int id;
	    private String name;
	    private String description;
		public String clientName;


}
