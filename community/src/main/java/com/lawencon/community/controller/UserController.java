package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.User;
import com.lawencon.community.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("users")
@PreAuthorize("hasAuthority('ROLSA', 'ROLAM', 'ROLMM')")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("{id}")
	public ResponseEntity<User> getById(@PathVariable("id") String id){
		final User result = userService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity<ResponseDto> update(@RequestBody User data){
		final ResponseDto result = userService.update(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
