package com.CrmService.dto;



import java.sql.Timestamp;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class TaskDto {
	private int id;
	private String category;
	private String client;
	private String title;
	private String description;
	private String dedline;
	private Timestamp startingDate;
	private String status;
	private String project;
	private String assingedBy;
	private String team;
	private String username;
	 private Timestamp  createdDate;
	 private String totalHours;
	 private int startworkingHours;
	 private int endworkingHours;
	 private String creatingDate;
	 private String deletedTag;
	 private String remarks;
	 private String employeeName;
	 private Timestamp endDate;


}
