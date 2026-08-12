package com.CrmService.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.CrmService.dto.Message;
import com.CrmService.dto.TeamDto;
import com.CrmService.mapper.TeamMapper;
import com.CrmService.model.Team;
import com.CrmService.repository.TeamRepository;
import com.CrmService.service.TeamService;
import com.CrmService.util.Constants;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class TeamServiceImpl implements TeamService {

	private final TeamMapper teamMapper;
	private final TeamRepository teamRepository;

	public TeamServiceImpl(TeamMapper teamMapper, TeamRepository teamRepository) {
		super();
		this.teamMapper = teamMapper;
		this.teamRepository = teamRepository;
	}

	@Override
	public Message<TeamDto> addTeam(TeamDto dto) {
		Message<TeamDto> message = new Message<>();
		try {
			Team team = teamRepository.getByName(dto.getName());
			if (team == null) {
				team = teamMapper.toTeamEntity(dto);
				team = teamRepository.save(team);
				log.info("Team added successfully with request: {}", dto);
				message.setStatus(HttpStatus.CREATED);
				message.setResponseMessage(Constants.TEAM_ADD);
				message.setData(teamMapper.toTeamDto(team));
				return message;

			} else {
				message.setResponseMessage(Constants.TEAM_AVAILABLE);
				log.error("Error facing in AddTeam(): {}", message.getResponseMessage());
				message.setStatus(HttpStatus.CONFLICT);
				return message;

			}
		} catch (Exception e) {
			log.error("In TeamService AddTeam() exception: ", e);
			message.setResponseMessage("Failed to add team: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}

	}

	@Override
	public Message<TeamDto> updateTeam(TeamDto dto) {
		Message<TeamDto> message = new Message<>();
		try {
			// Fetch the existing team entity
			Team existingTeam = teamRepository.getById(dto.getId());
			if (existingTeam == null) {
				message.setResponseMessage(Constants.TEAM_NOT_FOUND);
				log.error("Error facing in updateTeam(): {}", message.getResponseMessage());
				message.setStatus(HttpStatus.CONFLICT);
				return message;
			}

			// Use the mapper to convert the DTO into an entity, preserving the existing ID
			Team updatedTeamEntity = teamMapper.toTeamEntity(dto);
			updatedTeamEntity.setId(existingTeam.getId()); // Ensure the ID is preserved

			// Save the updated entity
			Team updatedTeam = teamRepository.save(updatedTeamEntity);

			log.info("Team updated successfully with request: {}", dto);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.TEAM_UPDATE);
			message.setData(teamMapper.toTeamDto(updatedTeam));
			return message;

		} catch (Exception e) {
			log.error("In TeamService updateTeam() exception: ", e);
			message.setResponseMessage("Failed to update team: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<TeamDto> deleteTeam(int id) {
		Message<TeamDto> message = new Message<>();
		try {
			// Use findById instead of getById
			Optional<Team> optionalTeam = teamRepository.findById(id);
			if (!optionalTeam.isPresent()) {
				message.setResponseMessage(Constants.TEAM_NOT_FOUND);
				log.error("Error in deleteTeam(): {}", message.getResponseMessage());
				message.setStatus(HttpStatus.CONFLICT);
				return message;
			}

			// Delete the team
			teamRepository.deleteById(id);
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.TEAM_DELETE);
			log.info("Team deleted successfully with ID: {}", id);
			return message;

		} catch (Exception e) {
			log.error("In TeamService deleteTeam() exception: ", e);
			message.setResponseMessage("Failed to delete team: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<TeamDto> GetTeamByName(String name) {
		Message<TeamDto> message = new Message<>();
		try {
			Team team = teamRepository.getByName(name);
			if (team == null) {
				message.setResponseMessage(Constants.TEAM_NOT_FOUND);
				log.error("Error facing in GetTeamByName(): {}", message.getResponseMessage());
				message.setStatus(HttpStatus.CONFLICT);
				return message;
			}
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage(Constants.RECORD_FOUND);
			message.setData(teamMapper.toTeamDto(team));
			log.info("Team found successfully with request: {}", name);
			return message;
		} catch (Exception e) {
			log.error("In TeamService AddTeam() exception: ", e);
			message.setResponseMessage("Failed to add team: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<TeamDto> GetTeamByManagerName(String manegerName) {
		Message<TeamDto> message = new Message<>();
		Team team = teamRepository.getByManagerName(manegerName);
		try {
			if (team != null) {
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage(Constants.RECORD_FOUND);
				message.setData(teamMapper.toTeamDto(team));
				log.info("Team found successfully with request: {}", manegerName);
				return message;
			} else {
				message.setResponseMessage(Constants.TEAM_NOT_FOUND);
				log.error("Error facing in GetTeamByManagerName(): {}", message.getResponseMessage());
				message.setStatus(HttpStatus.CONFLICT);
				return message;
			}
		} catch (Exception e) {
			log.error("In TeamService AddTeam() exception: ", e);
			message.setResponseMessage("Failed to add team: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	public List<Message<TeamDto>> getAllTeam() {
		List<Message<TeamDto>> messages = new ArrayList<>();
		try {
			List<Team> teams = teamRepository.findAll(); // Fetch all teams from the repository

			if (!teams.isEmpty()) {
				for (Team team : teams) {
					Message<TeamDto> message = new Message<>();
					message.setStatus(HttpStatus.OK);
					message.setResponseMessage(Constants.RECORD_FOUND);
					message.setData(teamMapper.toTeamDto(team)); // Convert Team to TeamDto
					messages.add(message);
				}
				log.info("All teams fetched successfully");
			} else {
				// Handle the case when no teams are found
				Message<TeamDto> message = new Message<>();
				message.setStatus(HttpStatus.NO_CONTENT);
				message.setResponseMessage(Constants.NO_RECORDS_FOUND); // Define a constant for this message
				log.warn("No teams found");
				messages.add(message);
			}
		} catch (Exception e) {
			// Handle exceptions
			log.error("Exception in getAllTeam(): ", e);
			Message<TeamDto> errorMessage = new Message<>();
			errorMessage.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			errorMessage.setResponseMessage("Failed to fetch teams: " + e.getMessage());
			messages.add(errorMessage);
		}
		return messages;
	}

}
