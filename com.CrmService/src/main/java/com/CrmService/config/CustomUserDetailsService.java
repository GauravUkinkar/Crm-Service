package com.CrmService.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.CrmService.dto.UserDto;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		String url = "https://userservice.pandozasolutions.com/AuthController/getUserByemail/{email}";
		try {
			// Get raw JSON string response from Auth service with path variable
			String result = restTemplate.getForObject(url, String.class, username);

			// Parse JSON string to JSONObject
			JSONObject json = new JSONObject(result);

			// Extract "data" object from JSON
			JSONObject jsonData = json.getJSONObject("data");

			// Convert JSONObject 'data' to UserDto using Jackson ObjectMapper
			ObjectMapper mapper = new ObjectMapper();
			UserDto user = mapper.readValue(jsonData.toString(), UserDto.class);

			if (user == null) {
				throw new UsernameNotFoundException("User not found: " + username);
			}

			String rolesString = user.getRole(); // e.g. "ADMIN,EMPLOYEE"

			List<GrantedAuthority> authorities = Collections.emptyList();
			if (rolesString != null && !rolesString.isEmpty()) {
				authorities = Arrays.stream(rolesString.split(",")).map(String::trim).map(role -> "ROLE_" + role)
						.map(SimpleGrantedAuthority::new).collect(Collectors.toList());
			}

			return new org.springframework.security.core.userdetails.User(user.getEmail(), "N/A", authorities);

		} catch (HttpClientErrorException e) {
			throw new UsernameNotFoundException("User not found: " + username);
		} catch (Exception e) {
			throw new UsernameNotFoundException("Error parsing user data for: " + username, e);

		}
	}

}
