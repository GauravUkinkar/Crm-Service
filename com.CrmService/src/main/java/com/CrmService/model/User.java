//package com.CrmService.model;
//
//import java.util.List;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//import jakarta.persistence.OneToMany;
//import lombok.Data;
//import lombok.ToString;
//import lombok.experimental.Accessors;
//
//@Data
//@ToString 
//@Entity
//@Accessors(chain = true)
//public class User {
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
//	private String employeeId;
//	private String username;
//	private String password;
//	private String employeeName;
//
//	
//	@ManyToOne(cascade = CascadeType.MERGE) // Use MERGE to ensure the role is persisted
//    @JoinColumn(name = "role_id", referencedColumnName = "id")
//    private Role role;
//	@ManyToOne(cascade = CascadeType.MERGE) // Use MERGE to ensure the role is persisted
//    @JoinColumn(name = "team_id", referencedColumnName = "id")
//    private Team team;
//	 @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//	    private List<TaskTracking> tasks;
//
//	
//
//}
