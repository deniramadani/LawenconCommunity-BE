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
import com.lawencon.community.model.Schedule;
import com.lawencon.community.service.ProductService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("products")
public class ProductController {

	@Autowired
	private ProductService productService;

	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping
	public ResponseEntity<ResponseDto> insert(@RequestBody final Schedule data){
		final ResponseDto result = productService.insert(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PutMapping
	public ResponseEntity<ResponseDto> update(@RequestBody final Schedule data){
		final ResponseDto result = productService.update(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping
	public ResponseEntity<List<Schedule>> getAllSchedule(@RequestParam(required = true) final Integer start,
			@RequestParam(required = true) final Integer limit){
		List<Schedule> result = new ArrayList<>(); 
		result = productService.getAllSchedule(start, limit);						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("{id}")
	public ResponseEntity<Schedule> getById(@PathVariable("id") final String id){
		final Schedule result = productService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("users")
	public ResponseEntity<List<Schedule>> getAllByUserId(@RequestParam(required = true) final Integer start,
			@RequestParam(required = true) final Integer limit){
		List<Schedule> result = new ArrayList<>(); 
		result = productService.getAllByUserId(start, limit);						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
