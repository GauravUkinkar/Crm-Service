package com.CrmService.dto;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class TeamDto {
	private int id;
	private String name;
	private int memberCount;
	private String manegerName;
	private int idealCount;
}
