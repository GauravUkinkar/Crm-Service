package com.CrmService.mapperImpl;

import org.springframework.stereotype.Component;

import com.CrmService.dto.ProjectDTO;
import com.CrmService.mapper.ProjectMapper;
import com.CrmService.model.Project;

@Component
public class ProjectMapperImpl implements ProjectMapper {

	@Override
	public ProjectDTO toProjectDTO(Project project) {
		ProjectDTO dto = new ProjectDTO();
        dto.setId(project.getId());
        dto.setName(project.getName());
        dto.setDescription(project.getDescription());
       dto.setClientName(project.getClientName());
        return dto;
	}

	@Override
	public Project toProjectEntity(ProjectDTO dto) {
		Project project = new Project();
        project.setId(dto.getId());
        project.setName(dto.getName());
        project.setDescription(dto.getDescription());
        project.setClientName(dto.getClientName());
        return project;
	}

}
