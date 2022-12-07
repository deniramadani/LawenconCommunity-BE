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
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Comment;
import com.lawencon.community.service.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("comments")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("/post/{id}")
	public ResponseEntity<List<Comment>> getByAll(@PathVariable String id) {
		final List<Comment> result = commentService.getAllByPost(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping
	public ResponseEntity<ResponseDto> insert(@RequestBody Comment data) {
		final ResponseDto res = commentService.insert(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PutMapping
	public ResponseEntity<ResponseDto> update(@RequestBody final Comment data){
		final ResponseDto result = commentService.update(data);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@DeleteMapping("delete/{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable("id") final String id) {
		final ResponseDto result = commentService.deleteById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
