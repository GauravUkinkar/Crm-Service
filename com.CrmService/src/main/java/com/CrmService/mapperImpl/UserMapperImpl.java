package com.CrmService.mapperImpl;

import org.springframework.stereotype.Component;

import com.CrmService.dto.LoginResponseDto;
import com.CrmService.dto.RegisterUserDto;
import com.CrmService.dto.RoleDto;
import com.CrmService.dto.TeamDto;
import com.CrmService.dto.UserResponseDto;
import com.CrmService.mapper.UserMapper;
import com.CrmService.model.User;

@Component
public class UserMapperImpl implements UserMapper {


	    public User userDtoTouser(RegisterUserDto userDto) {
	        User user = new User();
	        user.setEmployeeId(userDto.getEmployeeId());
	        user.setUsername(userDto.getUsername());
	        user.setPassword(userDto.getPassword());
	        user.setEmployeeName(userDto.getEmployeeName());
	        return user;
	    }

	    public RegisterUserDto userToDto(User user) {
	        RegisterUserDto userDto = new RegisterUserDto();
	        userDto.setId(user.getId());
	        userDto.setEmployeeId(user.getEmployeeId());
	        userDto.setUsername(user.getUsername());
	        userDto.setPassword(user.getPassword());
	        userDto.setRoleid(user.getRole().getId());
	        if (user.getTeam() != null) {
	        	userDto.setTeamid(user.getTeam().getId());
	        }
	        userDto.setEmployeeName(user.getEmployeeName());
	        
	        return userDto;
	    }

	    @Override
	    public UserResponseDto userToUserResponseDto(User user) {
	        UserResponseDto dto = new UserResponseDto();
	        dto.setId(user.getId());
	        dto.setEmployeeId(user.getEmployeeId());
	        dto.setUsername(user.getUsername());
            dto.setPassword(user.getPassword());
            dto.setEmployeeName(user.getEmployeeName());
	        // Mapping the Role details
	        RoleDto roleDto = new RoleDto();
	        roleDto.setId(user.getRole().getId());
	        roleDto.setName(user.getRole().getName());
	        dto.setRole(roleDto);

	        // Mapping the Team details with null check
	        if (user.getTeam() != null) {
	            TeamDto teamDto = new TeamDto();
	            teamDto.setId(user.getTeam().getId());
	            teamDto.setName(user.getTeam().getName());
	            teamDto.setMemberCount(user.getTeam().getMemberCount());
	            teamDto.setIdealCount(user.getTeam().getIdealCount());
	            teamDto.setManegerName(user.getTeam().getManegerName());
	            dto.setTeam(teamDto);
	        } else {
	            // Optionally, set teamDto to null or use default values
	            dto.setTeam(null); // Or provide default values if necessary
	        }

	        return dto;
	    }
	    
	    @Override
	    public UserResponseDto userToUserResponseDtoForAdmin(User user) {
	        UserResponseDto dto = new UserResponseDto();
	        dto.setId(user.getId());
	        dto.setUsername(user.getUsername());
	        dto.setPassword(user.getPassword());
	        dto.setEmployeeId(user.getEmployeeId());
	        dto.setEmployeeName(user.getEmployeeName());

	        // Mapping the Role details
	        RoleDto roleDto = new RoleDto();
	        roleDto.setId(user.getRole().getId());
	        roleDto.setName(user.getRole().getName());
	        dto.setRole(roleDto);

	        // Mapping the Team details with null check
	        if (user.getTeam() != null) {
	            TeamDto teamDto = new TeamDto();
	            teamDto.setId(user.getTeam().getId());
	            teamDto.setName(user.getTeam().getName());
	            teamDto.setMemberCount(user.getTeam().getMemberCount());
	            teamDto.setIdealCount(user.getTeam().getIdealCount());
	            teamDto.setManegerName(user.getTeam().getManegerName());
	            dto.setTeam(teamDto);
	        } else {
	            // Optionally, set teamDto to null or use default values
	            dto.setTeam(null); // Or provide default values if necessary
	        }

	        return dto;
	    }

		@Override
		public LoginResponseDto createLoginResponse(User user) {
			LoginResponseDto response = new LoginResponseDto();
			UserResponseDto getUserDto = convertUserToUserDto(user);
			response.setData(getUserDto);
			return response;

	}

		private UserResponseDto convertUserToUserDto(User user) {
		    UserResponseDto dto = new UserResponseDto();
		    dto.setId(user.getId());
		    dto.setEmployeeName(user.getEmployeeName());
		    dto.setUsername(user.getUsername());

		    // Map Role to RoleDto
		    if (user.getRole() != null) {
		        RoleDto roleDto = new RoleDto();
		        roleDto.setId(user.getRole().getId());
		        roleDto.setName(user.getRole().getName());
		        dto.setRole(roleDto); // Set role details
		    }
		    if(user.getTeam() != null) {
		    	TeamDto teamDto = new TeamDto();
		    	teamDto.setId(user.getTeam().getId());
		    	teamDto.setName(user.getTeam().getName());
		    	teamDto.setMemberCount(user.getTeam().getMemberCount());
		    	teamDto.setIdealCount(user.getTeam().getIdealCount());
		    	teamDto.setManegerName(user.getTeam().getManegerName());	
		    	dto.setTeam(teamDto);
		    }

		    return dto;
		}
}

