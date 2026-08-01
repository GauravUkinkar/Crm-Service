package com.CrmService.service;

import java.util.List;

import com.CrmService.dto.ChangePasswordDto;
import com.CrmService.dto.LoginRequest;
import com.CrmService.dto.LoginResponseDto;
import com.CrmService.dto.Message;
import com.CrmService.dto.RegisterUserDto;
import com.CrmService.dto.UserResponseDto;

public interface UserService {
Message<RegisterUserDto> register(RegisterUserDto userDto);
Message<RegisterUserDto> updateUser(RegisterUserDto userDto);

public Message<UserResponseDto> getUserByUsername(String username);
public Message<LoginResponseDto> userLogin(LoginRequest request);
public Message<List<UserResponseDto>> getAllUsers();
public Message<UserResponseDto> changePassword(ChangePasswordDto dto);
public Message<UserResponseDto>deleteUser(int id);
public Message<List<UserResponseDto>>getallUserSByTeamName(String teamName); 
public Message<UserResponseDto>getUserById(int id);

}
