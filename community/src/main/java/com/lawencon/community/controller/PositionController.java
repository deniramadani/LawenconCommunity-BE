package com.lawencon.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Position;
import com.lawencon.community.service.PositionService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("positions")
public class PositionController {

	@Autowired
	private PositionService positionService;
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping
	public ResponseEntity<List<Position>> getByAll(@RequestParam(required = true) final Integer start,
			@RequestParam(required = true) final Integer limit) {
		final List<Position> result = positionService.getAll(start, limit);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@PostMapping
	public ResponseEntity<ResponseDto> insert(@RequestBody final Position data){
		final ResponseDto result = positionService.insert(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@PutMapping
	public ResponseEntity<ResponseDto> update(@RequestBody final Position data){
		final ResponseDto result = positionService.update(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("{id}")
	public ResponseEntity<Position> getById(@PathVariable("id") final String id){
		final Position result = positionService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable("id") String id) {
		final ResponseDto res = positionService.deleteById(id);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
}
