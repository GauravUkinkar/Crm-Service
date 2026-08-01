package com.CrmService.mapper;

import com.CrmService.dto.TeamDto;
import com.CrmService.model.Team;

public interface TeamMapper {
	 public TeamDto toTeamDto(Team team);
	 public Team toTeamEntity(TeamDto dto);
}
