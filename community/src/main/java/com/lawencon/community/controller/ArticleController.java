package com.lawencon.community.controller;

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
import com.lawencon.community.model.Article;
import com.lawencon.community.service.ArticleService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("articles")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@PostMapping
	public ResponseEntity<ResponseDto> insert(@RequestBody final Article data){
		final ResponseDto result = articleService.insert(data);
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@PutMapping
	public ResponseEntity<ResponseDto> update(@RequestBody final Article data){
		final ResponseDto result = articleService.update(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("{id}")
	public ResponseEntity<Article> getById(@PathVariable("id") final String id){
		final Article result = articleService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping
	public ResponseEntity<List<Article>> getAll(@RequestParam(required = false) final Integer start,
			@RequestParam(required = false) final Integer limit){
		final List<Article> result = articleService.getAll(start, limit);						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("users")
	public ResponseEntity<List<Article>> getAllByUserId(@RequestParam(required = true) final Integer start,
			@RequestParam(required = true) final Integer limit){
		final List<Article> result = articleService.getAllByUserId(start, limit);						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@PutMapping("delete/{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable("id") final String id) {
		final ResponseDto result = articleService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
