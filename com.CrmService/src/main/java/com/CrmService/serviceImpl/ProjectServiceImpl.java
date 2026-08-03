package com.CrmService.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.CrmService.dto.GetProjectsDto;
import com.CrmService.dto.Message;
import com.CrmService.dto.ProjectDTO;
import com.CrmService.mapper.ProjectMapper;
import com.CrmService.model.Client;
import com.CrmService.model.Project;
import com.CrmService.repository.ClientRepository;
import com.CrmService.repository.ProjectsRepository;
import com.CrmService.service.ProjectService;
import com.CrmService.util.Constants;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProjectServiceImpl implements ProjectService {

	private final ProjectsRepository projectRepository;
	private final ClientRepository clientRepository;
	private final ProjectMapper projectMapper;

	public ProjectServiceImpl(ProjectsRepository projectRepository, ClientRepository clientRepository,
			ProjectMapper projectMapper) {
		super();
		this.projectRepository = projectRepository;
		this.clientRepository = clientRepository;
		this.projectMapper = projectMapper;
	}

	@Override
	public Message<ProjectDTO> addProject(ProjectDTO project) {
		Message<ProjectDTO> message = new Message<>();
		log.info("Incoming request body: {}", project);
		try {
			List<Project> projects = projectRepository.findAllByClientName(project.getClientName()); // Assuming a method like this exists in your repo

			for (Project pro : projects) {
			    if (pro.getName().equals(project.getName())) {
			        message.setStatus(HttpStatus.CONFLICT);
			        message.setResponseMessage(Constants.PROJECT_AVAILABLE);
			        log.error("Error facing in AddProject(): {}", message.getResponseMessage());
			        return message;
			    }
			}
			Client cli = clientRepository.getByName(project.getClientName());
			if (cli == null) {
				message.setStatus(HttpStatus.CONFLICT);
				message.setResponseMessage(Constants.CLIENT_NOT_FOUND);
				log.error("Error facing in AddProject(): {}", message.getResponseMessage());
				return message;
			}
			Project pr = projectMapper.toProjectEntity(project);
			projectRepository.save(pr);
			message.setStatus(HttpStatus.CREATED);
			message.setResponseMessage(Constants.PROJECT_ADD);
			message.setData(projectMapper.toProjectDTO(pr));
			log.info("Project added successfully with request: {}", project);
			return message;
		} catch (Exception e) {
			log.error("In ProjectService AddProject() exception: ", e);
			message.setResponseMessage("Failed to add project: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<ProjectDTO> updateProject(ProjectDTO project) {
		Message<ProjectDTO> message = new Message<>();
		log.info("Incoming request body: {}", project);
		try {
			Project pro = projectRepository.getById(project.getId());
			if (pro == null) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.PROJECT_NOT_FOUND);
				log.error("Error facing in UpdateProject(): {}", message.getResponseMessage());
				return message;
			}
			Project pro1 = projectRepository.getByName(project.getName());
			if (pro1 != null && pro1.getName().equals(project.getName())) {
				message.setStatus(HttpStatus.CONFLICT);
				message.setResponseMessage(Constants.PROJECT_AVAILABLE);
				log.error("Error facing in AddProject(): {}", message.getResponseMessage());
				return message;
			}
			Client cli = clientRepository.getByName(project.getClientName());
			if (cli == null) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.CLIENT_NOT_FOUND);
				log.error("Error facing in UpdateProject(): {}", message.getResponseMessage());
				return message;
			}
			Project pr = projectMapper.toProjectEntity(project);
			projectRepository.save(pr);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.PROJECT_UPDATE);
			message.setData(projectMapper.toProjectDTO(pr));
			log.info("Project updated successfully with request: {}", project);
			return message;
		} catch (Exception e) {
			log.error("In ProjectService AddProject() exception: ", e);
			message.setResponseMessage("Failed to add project: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<ProjectDTO> deleteProject(int id) {
		Message<ProjectDTO> message = new Message<>();
		log.info("Incoming request with projectID: {}", id);
		try {
			Project pro = projectRepository.getById(id);
			if (pro == null) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.PROJECT_NOT_FOUND);
				log.error("Error facing in UpdateProject(): {}", message.getResponseMessage());
				return message;
			}
			projectRepository.delete(pro);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.PROJECT_DELETE);
			log.info("Project deleted successfully with request: {}", id);
			return message;
		} catch (Exception e) {
			log.error("In ProjectService AddProject() exception: ", e);
			message.setResponseMessage("Failed to add project: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;

		}
	}

	@Override
	public Message<ProjectDTO> getProject(String name) {
		Message<ProjectDTO> message = new Message<>();
		log.info("Incoming request with projectName: {}", name);
		try {
			Project pro1 = projectRepository.getByName(name);
			if (pro1 == null) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.PROJECT_NOT_FOUND);
				log.error("Error facing in UpdateProject(): {}", message.getResponseMessage());
				return message;
			}
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.RECORD_FOUND);
			message.setData(projectMapper.toProjectDTO(pro1));
			log.info("Project found successfully with request: {}", name);
			return message;
		} catch (Exception e) {
			log.error("In ProjectService AddProject() exception: ", e);
			message.setResponseMessage("Failed to add project: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;

		}
	}

	@Override
	public Message<List<ProjectDTO>> getProjectsByClientName(GetProjectsDto request) {
		Message<List<ProjectDTO>> message = new Message<>();
		log.info("Incoming request with projects By ClientID: {}", request);

		try {
			List<Project> projects = projectRepository.findByClientName(request.getClientName()); // Assuming `findByClientId` is
																						// implemented to return
																						// multiple projects
			if (projects == null || projects.isEmpty()) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage(Constants.PROJECT_NOT_FOUND); // Change constant to reflect multiple projects
				log.error("Error finding projects for clientId: {}. {}", request, message.getResponseMessage());
				return message;
			}

			// Mapping each Project to ProjectDTO
			List<ProjectDTO> projectDTOs = projects.stream().map(projectMapper::toProjectDTO)
					.collect(Collectors.toList());

			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.RECORDS_FOUND); // Update the message to indicate multiple records
			message.setData(projectDTOs);
			log.info("Projects found successfully for clientId: {}", request);

			return message;
		} catch (Exception e) {
			log.error("In ProjectService getProjectsByClientId() exception: ", e);
			message.setResponseMessage("Failed to retrieve projects: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public List<ProjectDTO> getAllProjects() {
		return projectRepository.findAll().stream().map(projectMapper::toProjectDTO).collect(Collectors.toList());
	}

}
