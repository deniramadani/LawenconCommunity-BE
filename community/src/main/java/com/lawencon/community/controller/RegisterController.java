package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.User;
import com.lawencon.community.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("register")
public class RegisterController {

	@Autowired
	private UserService userService;
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping("/super-admin")
	public ResponseEntity<ResponseDto> registerSuperAdmin(@RequestBody final User data) {
		final ResponseDto res = userService.insertSuperAdmin(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping("/admin")
	public ResponseEntity<ResponseDto> registerAdmin(@RequestBody final User data) {
		final ResponseDto res = userService.insertAdmin(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
	@PostMapping("/member")
	public ResponseEntity<ResponseDto> registerMember(@RequestBody final User data) {
		final ResponseDto res = userService.insertMember(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
}
