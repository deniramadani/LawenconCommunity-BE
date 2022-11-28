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
import com.lawencon.community.model.Industry;
import com.lawencon.community.service.IndustryService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("industries")
public class IndustryController {
	
	@Autowired
	private IndustryService industryService;

	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping
	public ResponseEntity<List<Industry>> getByAll(@RequestParam(required = true) final Integer start,
			@RequestParam(required = true) final Integer limit) {
		final List<Industry> result = industryService.getAll(start, limit);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PostMapping
	public ResponseEntity<ResponseDto> insert(@RequestBody final Industry data){
		final ResponseDto result = industryService.insert(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@PutMapping
	public ResponseEntity<ResponseDto> update(@RequestBody final Industry data){
		final ResponseDto result = industryService.update(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("{id}")
	public ResponseEntity<Industry> getById(@PathVariable("id") final String id){
		final Industry result = industryService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLSA')")
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable("id") final String id) {
		final ResponseDto res = industryService.deleteById(id);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
}
