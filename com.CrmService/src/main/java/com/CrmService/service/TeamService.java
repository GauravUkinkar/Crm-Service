package com.CrmService.service;

import java.util.List;

import com.CrmService.dto.Message;
import com.CrmService.dto.TeamDto;

public interface TeamService {
	public Message<TeamDto>addTeam(TeamDto dto);
	public Message<TeamDto>updateTeam(TeamDto dto);
	public Message<TeamDto>deleteTeam(int id);
	public Message<TeamDto>GetTeamByName(String name);
	public Message<TeamDto>GetTeamByManagerName(String manegerName);
	public List<Message<TeamDto>>getAllTeam();
	

}
