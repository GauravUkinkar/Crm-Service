package com.CrmService.config;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
	private final UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String jwt = null;
	    String username = null;

	    try {

	        // 1. Check Authorization Header
	        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

	        if (StringUtils.isNotEmpty(authHeader) && authHeader.startsWith("Bearer ")) {
	            jwt = authHeader.substring(7);
	        }

	        // 2. If Authorization header is not present, check Cookie
	        if (jwt == null && request.getCookies() != null) {
	            for (Cookie cookie : request.getCookies()) {
	                if ("token".equals(cookie.getName())) {
	                    jwt = cookie.getValue();
	                    break;
	                }
	            }
	        }

	        // No token found
	        if (jwt == null) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        // Extract username
	        try {
	            username = jwtUtil.extractUsername(jwt);
	        } catch (ExpiredJwtException e) {
	            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
	            return;
	        }

	        if (StringUtils.isNotEmpty(username)
	                && SecurityContextHolder.getContext().getAuthentication() == null) {

	            UserDetails userDetails =
	            		userDetailsService.loadUserByUsername(username);//userDetailsService().loadUserByUsername(username);

	            if (jwtUtil.isTokenValid(jwt, userDetails)) {

	                UsernamePasswordAuthenticationToken authentication =
	                        new UsernamePasswordAuthenticationToken(
	                                userDetails,
	                                null,
	                                userDetails.getAuthorities());

	                authentication.setDetails(
	                        new WebAuthenticationDetailsSource().buildDetails(request));

	                SecurityContext context = SecurityContextHolder.createEmptyContext();
	                context.setAuthentication(authentication);
	                SecurityContextHolder.setContext(context);
	            }
	        }

	        filterChain.doFilter(request, response);

	    } catch (ExpiredJwtException e) {
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expired");
	    } catch (Exception e) {
	        e.printStackTrace();
	        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
	    }
	}
}
