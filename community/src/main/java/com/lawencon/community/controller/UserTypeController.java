package com.lawencon.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.UserType;
import com.lawencon.community.service.UserTypeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("user-types")
@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
public class UserTypeController {

	@Autowired
	private UserTypeService userTypeService;

	@GetMapping
	public ResponseEntity<List<UserType>> getByAll() {
		final List<UserType> result = userTypeService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
