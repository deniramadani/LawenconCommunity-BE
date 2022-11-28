package com.lawencon.community.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.User;
import com.lawencon.community.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping
	public ResponseEntity<List<User>> getByAll(@RequestParam(required = false) final Integer start,
			@RequestParam(required = false) Integer limit){
		List<User> result = new ArrayList<>(); 
		if(start != null) {
			if (limit==null) {
				limit = 10;
			}
			 result = userService.getAll(start,limit);						
		}else {
			 result = userService.getAll();	
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("{id}")
	public ResponseEntity<User> getById(@PathVariable("id") final String id){
		final User result = userService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@PutMapping
	public ResponseEntity<ResponseDto> update(@RequestBody final User data){
		final ResponseDto result = userService.update(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping
	public ResponseEntity<ResponseDto> create(@RequestBody final User data){
		final ResponseDto result = userService.create(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PutMapping("delete/{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable("id") final String id){
		final ResponseDto result = userService.delete(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
