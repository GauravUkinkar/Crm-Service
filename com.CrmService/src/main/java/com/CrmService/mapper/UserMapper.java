package com.CrmService.mapper;

import com.CrmService.dto.LoginResponseDto;
import com.CrmService.dto.RegisterUserDto;
import com.CrmService.dto.UserResponseDto;
import com.CrmService.model.User;

public interface UserMapper {
	public User userDtoTouser(RegisterUserDto userDto);
	public RegisterUserDto userToDto(User user);
	public UserResponseDto userToUserResponseDto(User user);
	public LoginResponseDto createLoginResponse(User user);
	public UserResponseDto userToUserResponseDtoForAdmin(User user);

}
