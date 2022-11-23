package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.dashboard.DashboardDto;
import com.lawencon.community.service.DashboardService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("dashboards")
@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM')")
public class DashboardController {

	@Autowired
	private DashboardService dashboardService;

	@GetMapping
	public ResponseEntity<DashboardDto> getByAll() {
		final DashboardDto result = dashboardService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
