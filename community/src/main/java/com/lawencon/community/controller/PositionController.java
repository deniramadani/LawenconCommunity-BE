package com.lawencon.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.Position;
import com.lawencon.community.service.PositionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("positions")
@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
public class PositionController {

	@Autowired
	private PositionService positionService;

	@GetMapping
	public ResponseEntity<List<Position>> getByAll() {
		final List<Position> result = positionService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}