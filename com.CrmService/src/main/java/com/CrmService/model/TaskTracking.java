package com.CrmService.model;

import java.sql.Timestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString 
@Entity
@Accessors(chain = true)
public class TaskTracking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private User user;

}
