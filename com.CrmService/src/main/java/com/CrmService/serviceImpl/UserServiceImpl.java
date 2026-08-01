package com.CrmService.serviceImpl;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.CrmService.dto.ChangePasswordDto;
import com.CrmService.dto.LoginRequest;
import com.CrmService.dto.LoginResponseDto;
import com.CrmService.dto.Message;
import com.CrmService.dto.RegisterUserDto;
import com.CrmService.dto.UserResponseDto;
import com.CrmService.mapper.UserMapper;
import com.CrmService.model.Role;
import com.CrmService.model.TaskScheduler;
import com.CrmService.model.TaskStatus;
import com.CrmService.model.Team;
import com.CrmService.model.User;
import com.CrmService.repository.RoleRepository;
import com.CrmService.repository.TaskSchedulerRepository;
import com.CrmService.repository.TeamRepository;
import com.CrmService.repository.UserRepository;
import com.CrmService.service.UserService;
import com.CrmService.util.Constants;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final RoleRepository roleRepository;
	private final TeamRepository teamRepository;	
	@Autowired
	private TaskSchedulerRepository taskRepository;
//	private final JwtUtil jwtUtil;

	public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, RoleRepository roleRepository,
			TeamRepository teamRepository) {
		super();
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.roleRepository = roleRepository;
		this.teamRepository = teamRepository;
	}

	public Message<RegisterUserDto> register(RegisterUserDto userDto) {
		Message<RegisterUserDto> message = new Message<>();
		log.info("Incoming request body: {}", userDto);

		// Validate input 
		if (userDto == null || userDto.getUsername() == null || userDto.getPassword() == null
				|| userDto.getRoleid() == 0) {
			log.error("Invalid input: missing required fields");
			message.setResponseMessage("Invalid input: missing required fields");
			message.setStatus(HttpStatus.BAD_REQUEST);
			return message;
		}

		try {
			// Check if username already exists
			Optional<User> existingUser = userRepository.findByUsername(userDto.getUsername());
			if (existingUser.isPresent()) {
				log.error("Username already exists: {}", userDto.getUsername());
				message.setResponseMessage("Username already exists: " + userDto.getUsername());
				message.setStatus(HttpStatus.CONFLICT);
				return message;
			}

			// Fetch the Role from the database using the roleId from DTO
			Optional<Role> role = roleRepository.findById(userDto.getRoleid());
			if (role.isEmpty()) {
				log.error("Role not found for id: {}", userDto.getRoleid());
				message.setResponseMessage("Role not found for id: " + userDto.getRoleid());
				message.setStatus(HttpStatus.BAD_REQUEST);
				return message;
			}

			// Handle optional teamid
			Team team = null;
			if (userDto.getTeamid() != null) {
				Optional<Team> optionalTeam = teamRepository.findById(userDto.getTeamid());
				if (optionalTeam.isEmpty()) {
					log.error("Team not found for id: {}", userDto.getTeamid());
					message.setResponseMessage("Team not found for id: " + userDto.getTeamid());
					message.setStatus(HttpStatus.BAD_REQUEST);
					return message;
				}
				team = optionalTeam.get();
			}

			// Map DTO to User entity
			User newUser = userMapper.userDtoTouser(userDto);
			newUser.setRole(role.get());

			// Set team only if present
			if (team != null) {
				newUser.setTeam(team);

			}
			if (role.get().getId() == 3) {
			    // Check conditions only for role ID 3
			    if (userDto.getTeamid() != 0 && (team.getManegerName() == null || team.getManegerName().isEmpty())) {
			        // If the role is 3 (manager role), a team is provided, and there is no manager
			        team.setManegerName(userDto.getUsername());
			        teamRepository.save(team);
			    } else {
			        // If any of the above conditions fail
			        message.setResponseMessage("Call Update Team API to change Team Manager");
			        message.setStatus(HttpStatus.BAD_REQUEST);
			        return message;
			    }
			} else {
			   
			    log.info("Processing for role other than manager (Role ID: {}).", role.get().getId());
			}

			// Save the User
			newUser = userRepository.save(newUser);

			// Prepare success response
			message.setStatus(HttpStatus.CREATED);
			message.setResponseMessage("User created successfully.");
			message.setData(userMapper.userToDto(newUser));
			return message;

		} catch (Exception e) {
			log.error("In UserServiceImpl register() exception: {}", e.getMessage());
			message.setResponseMessage("Failed to register user: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<RegisterUserDto> updateUser(RegisterUserDto userDto) {
	    Message<RegisterUserDto> message = new Message<>();
	    log.info("Incoming request body: {}", userDto);

	    // Validate input
	    if (userDto == null || userDto.getUsername() == null || userDto.getPassword() == null
	            || userDto.getRoleid() == 0 || userDto.getId() == 0) {
	        log.error("Invalid input: missing required fields");
	        message.setResponseMessage("Invalid input: missing required fields");
	        message.setStatus(HttpStatus.BAD_REQUEST);
	        return message;
	    }

	    try {
	        
	        User existingUser = userRepository.findById(userDto.getId())
	                .orElseThrow(() -> {
	                    log.error("User not found for id: {}", userDto.getId());
	                    return new RuntimeException("User not found for id: " + userDto.getId());
	                });

	       
	        Role currentRole = existingUser.getRole();
	        if (currentRole == null) {
	            log.error("Existing user does not have a role assigned.");
	            message.setResponseMessage("Existing user does not have a role assigned.");
	            message.setStatus(HttpStatus.BAD_REQUEST);
	            return message;
	        }

	       
	        Role newRole = roleRepository.findById(userDto.getRoleid())
	                .orElseThrow(() -> {
	                    log.error("Role not found for id: {}", userDto.getRoleid());
	                    return new RuntimeException("Role not found for id: " + userDto.getRoleid());
	                });

	        
	        Team team = null;
	        if (userDto.getTeamid() != null) {
	            team = teamRepository.findById(userDto.getTeamid())
	                    .orElseThrow(() -> {
	                        log.error("Team not found for id: {}", userDto.getTeamid());
	                        return new RuntimeException("Team not found for id: " + userDto.getTeamid());
	                    });
	        }

	        
	        existingUser.setUsername(userDto.getUsername());
	        existingUser.setPassword(userDto.getPassword());
	        existingUser.setRole(newRole);
	        existingUser.setEmployeeId(userDto.getEmployeeId());
	        if (team != null) {
	            existingUser.setTeam(team);
	        }

	       
	        User updatedUser = userRepository.save(existingUser);

	        // Prepare success response
	        message.setStatus(HttpStatus.OK);
	        message.setResponseMessage("User updated successfully.");
	        message.setData(userMapper.userToDto(updatedUser));
	        return message;

	    } catch (Exception e) {
	        log.error("In UserServiceImpl updateUser() exception: {}", e.getMessage());
	        message.setResponseMessage("Failed to update user: " + e.getMessage());
	        message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
	        return message;
	    }
	}


	@Override
	public Message<UserResponseDto> getUserById(int id) {
		Message<UserResponseDto> message = new Message<>();

		try {
			// Find user by username
			User user = userRepository.getById(id);
			if (user==null) {
				message.setResponseMessage("User not found with Id: " + id);
				message.setStatus(HttpStatus.NOT_FOUND);
				return message;
			}

			
			UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user);
            message.setStatus(HttpStatus.OK);
			message.setResponseMessage("User found");
			message.setData(userResponseDto);
			return message;

		} catch (Exception e) {
			log.error("In UserServiceImpl getUserById() exception: {}", e.getMessage());
			message.setResponseMessage("Failed to get user details: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}
	

	@Override
	public Message<LoginResponseDto> userLogin(LoginRequest request) {
		log.info("Incoming request body: {}", request);
		Message<LoginResponseDto> message = new Message<>();
		try {
			User user = userRepository.getByUsername(request.getUsername());
			if (user != null) {
				// Check if the password matches before attempting authentication
				if (request.getPassword().equals(user.getPassword())) {
					// Authentication successful
					
					TaskScheduler schedular = new TaskScheduler();
					schedular.setUserId(user.getId());
					schedular.setStatus(TaskStatus.ONLINE);
					schedular.setLast_seen(LocalTime.now().withNano(0));
					System.out.println(LocalTime.now().withNano(0));
					schedular.setDate(new Date());
					taskRepository.save(schedular);
					
					LoginResponseDto responses = userMapper.createLoginResponse(user);
//	                String token = jwtUtil.generateToken(user);
//	                jwtUtil.printTokenData(token);
//	                responses.setToken(token);
					responses.setMessage(Constants.LOGIN_SUCCESSFULL);
					message.setData(responses);
					message.setResponseMessage(Constants.LOGIN_SUCCESSFULL);
					message.setStatus(HttpStatus.OK);
					return message;
				} else {
					// Incorrect password
					log.error("Error occurred In UserServiceImpl login(): INVALID_PASSWORD");
					message.setResponseMessage(Constants.INVALID_PASSWORD);
					message.setStatus(HttpStatus.UNAUTHORIZED);
					return message;
				}
			} else {
				// User not found or deleted
				message.setResponseMessage(Constants.USER_NOT_EXISTS_OR_DELETED);
				message.setStatus(HttpStatus.CONFLICT);
				return message;
			}
		} catch (Exception e) {
			// Internal server error
			log.error("Error occurred In UserServiceImpl login(): " + e.getMessage());
			message.setResponseMessage(Constants.INTERNAL_SERVER_ERROR);
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}

	}

	@Override
	public Message<UserResponseDto> getUserByUsername(String username) {
		Message<UserResponseDto> message = new Message<>();

		try {
			// Find user by username
			Optional<User> user = userRepository.findByUsername(username);
			if (user.isEmpty()) {
				message.setResponseMessage("User not found with username: " + username);
				message.setStatus(HttpStatus.NOT_FOUND);
				return message;
			}

			// Map the User entity to UserResponseDto
			UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user.get());

			// Prepare success response
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("User found");
			message.setData(userResponseDto);
			return message;

		} catch (Exception e) {
			log.error("In UserServiceImpl getUserByUsername() exception: {}", e.getMessage());
			message.setResponseMessage("Failed to get user details: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	public Message<List<UserResponseDto>> getAllUsers() {
		Message<List<UserResponseDto>> message = new Message<>();
		try {
			List<User> users = userRepository.findAll();
			List<UserResponseDto> userDtos = users.stream().map(userMapper::userToUserResponseDtoForAdmin)
					.collect(Collectors.toList());
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("Users found successfully.");
			message.setData(userDtos);
			return message;
		} catch (Exception e) {
			log.error("Exception in getAllUsers: ", e);
			throw new RuntimeException("Failed to fetch users: " + e.getMessage());
		}
	}

	@Override
	public Message<UserResponseDto> changePassword(ChangePasswordDto dto) {
		Message<UserResponseDto> message = new Message<>();
		try {
			Optional<User> user = userRepository.findByUsername(dto.getUsername());
			if (user.isEmpty()) {
				message.setResponseMessage("User not found with username: " + dto.getUsername());
				message.setStatus(HttpStatus.NOT_FOUND);
				return message;
			}
			if(dto.getConfirmPassword() == null || !dto.getConfirmPassword().equals(dto.getNewPassword())) {
				message.setStatus(HttpStatus.BAD_REQUEST);
				message.setResponseMessage("Password  and confirm password should be same.");
				return message;
			}
				user.get().setPassword(dto.getNewPassword());
				userRepository.save(user.get());
				UserResponseDto userResponseDto = userMapper.userToUserResponseDto(user.get());
				message.setStatus(HttpStatus.OK);
				message.setResponseMessage("Password changed successfully.");
				message.setData(userResponseDto);
				return message;
			
		} catch (Exception e) {
			log.error("In UserServiceImpl getUserByUsername() exception: {}", e.getMessage());
			message.setResponseMessage("Failed to get user details: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return message;
		}
	}

	@Override
	public Message<UserResponseDto> deleteUser(int id) {
		Message<UserResponseDto> message = new Message<>();
		Team team = new Team();
		try {
			if (!userRepository.existsById(id)) {
				message.setStatus(HttpStatus.NOT_FOUND);
				message.setResponseMessage("User not found");
				return message;
			}

			userRepository.deleteById(id); // Cascade deletion will remove associated tasks
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("User and associated tasks deleted successfully");

		} catch (Exception e) {
			log.error("Error in deleteUser(): ", e);
			message.setResponseMessage("Failed to delete user: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return message;
	}

	@Override
	public Message<List<UserResponseDto>> getallUserSByTeamName(String teamName) {
		Message<List<UserResponseDto>> message = new Message<>();
		try {
			// Fetch users by team name
			List<User> users = userRepository.findByTeamName(teamName);

			// Map users to UserResponseDto
			List<UserResponseDto> userDtos = users.stream().map(userMapper::userToUserResponseDtoForAdmin)
					.collect(Collectors.toList());

			// Prepare success response
			message.setStatus(HttpStatus.OK);
			message.setResponseMessage("Users found successfully.");
			message.setData(userDtos);
		} catch (Exception e) {
			// Log and prepare failure response
			log.error("Error in getallUserSByTeamName(): ", e);
			message.setResponseMessage("Failed to fetch users: " + e.getMessage());
			message.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return message;
	}

	

}
