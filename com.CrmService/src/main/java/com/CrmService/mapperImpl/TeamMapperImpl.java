package com.CrmService.mapperImpl;

import org.springframework.stereotype.Component;

import com.CrmService.dto.TeamDto;
import com.CrmService.mapper.TeamMapper;
import com.CrmService.model.Team;

@Component
public class TeamMapperImpl implements TeamMapper {

	@Override
	public TeamDto toTeamDto(Team team) {
		TeamDto dto = new TeamDto();
		dto.setId(team.getId());
		dto.setName(team.getName());
		dto.setMemberCount(team.getMemberCount());
		dto.setIdealCount(team.getIdealCount());
		dto.setManegerName(team.getManegerName());
		return dto;
	}

	@Override
	public Team toTeamEntity(TeamDto dto) {
		Team team = new Team();
		team.setName(dto.getName());
		team.setMemberCount(dto.getMemberCount());
		team.setIdealCount(dto.getIdealCount());
		team.setManegerName(dto.getManegerName());
		return team;
	}

}
