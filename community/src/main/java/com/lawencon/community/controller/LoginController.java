package com.lawencon.community.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.login.LoginResDto;
import com.lawencon.community.model.User;
import com.lawencon.community.service.UserService;
import com.lawencon.util.JwtUtil;
import com.lawencon.util.JwtUtil.ClaimKey;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("login")
public class LoginController {
	
	@Autowired
	private AuthenticationManager authManager;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private UserService userService;
	
	@PostMapping
	public ResponseEntity<LoginResDto> login(@RequestBody final User data) {
		final Authentication auth = new UsernamePasswordAuthenticationToken(data.getEmail(), data.getPassword());
		authManager.authenticate(auth);
		final Optional<User> user = userService.getByEmail(data.getEmail());
		
		final Map<String, Object> claims = new HashMap<>();
		claims.put(ClaimKey.ID.name(), user.get().getId());
		claims.put(ClaimKey.ROLE.name(), user.get().getRole().getRoleCode());
		
		
		final LoginResDto res = new LoginResDto();
		res.setId(user.get().getId());
		res.setFullname(user.get().getFullname());
		res.setRoleCode(user.get().getRole().getRoleCode());
		res.setUserTypeCode(user.get().getUserType().getUserTypeCode());
		res.setToken(jwtUtil.generateToken(claims));
		return new ResponseEntity<LoginResDto>(res, HttpStatus.OK);
	}
	
}