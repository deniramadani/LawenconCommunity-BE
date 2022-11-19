package com.lawencon.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.Industry;
import com.lawencon.community.service.IndustryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("industries")
@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
public class IndustryController {

	@Autowired
	private IndustryService industryService;

	@GetMapping
	public ResponseEntity<List<Industry>> getByAll() {
		final List<Industry> result = industryService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
