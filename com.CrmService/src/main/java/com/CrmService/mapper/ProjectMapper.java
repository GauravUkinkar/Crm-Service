package com.CrmService.mapper;


import com.CrmService.dto.ProjectDTO;
import com.CrmService.model.Project;

public interface ProjectMapper {
	public ProjectDTO toProjectDTO(Project project);
	 public Project toProjectEntity(ProjectDTO dto);
	 
}
