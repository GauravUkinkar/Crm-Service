package com.CrmService.service;

import java.util.List;

import com.CrmService.dto.GetProjectsDto;
import com.CrmService.dto.Message;
import com.CrmService.dto.ProjectDTO;

public interface ProjectService {
	public Message<ProjectDTO> addProject(ProjectDTO project);
	public Message<ProjectDTO> updateProject(ProjectDTO project);
	public Message<ProjectDTO> deleteProject(int id);
	public Message<ProjectDTO> getProject(String name);
	public List<ProjectDTO> getAllProjects();
	public Message<List<ProjectDTO>> getProjectsByClientName(GetProjectsDto request);

}
