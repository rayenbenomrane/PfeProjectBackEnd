package com.example.configuration;

import java.io.IOException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.jwt.UserService;
import com.example.utils.JwtUtils;


@Component
@RequiredArgsConstructor
public class JwtAuthentificationFilter extends OncePerRequestFilter {

		@Autowired
	    private JwtUtils JwtUtil;
	    @Autowired
	    private UserService userService;
	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
	final String authHeader=request.getHeader("Authorization");
	final String jwt;
	final String userEmail;
	logger.info(authHeader);
	if(StringUtils.isEmpty(authHeader)){
	    filterChain.doFilter(request,response);
	    return;
	}
	jwt=authHeader.substring(7);
	userEmail=JwtUtil.extractUsername(jwt);
	if(StringUtils.isNotEmpty(userEmail)&& SecurityContextHolder.getContext().getAuthentication()==null){
	    UserDetails userDetails=userService.userDetailsService().loadUserByUsername(userEmail);
	    if(JwtUtil.isTokenValid(jwt,userDetails)) {
	    	logger.info("JWT token is valid and user authentication is set.");
	        SecurityContext context = SecurityContextHolder.createEmptyContext();
	        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
	                userDetails, null, userDetails.getAuthorities());
	        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	        context.setAuthentication(authToken);
	        SecurityContextHolder.setContext(context);
	    }
	}
	filterChain.doFilter(request,response);
	    }

}
