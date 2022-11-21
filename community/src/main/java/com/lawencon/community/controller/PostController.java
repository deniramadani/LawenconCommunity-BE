package com.lawencon.community.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Post;
import com.lawencon.community.service.PostService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("posts")
@PreAuthorize("hasAuthority('ROLMM')")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@PostMapping("basic")
	public ResponseEntity<ResponseDto> insertBasic(@RequestBody Post data) {
		ResponseDto res = postService.insertBasic(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
	@PostMapping("premium")
	public ResponseEntity<ResponseDto> insertPremium(@RequestBody Post data) {
		ResponseDto res = postService.insertPremium(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
	@PostMapping("polling")
	public ResponseEntity<ResponseDto> insertPolling(@RequestBody Post data) {
		ResponseDto res = postService.insertPolling(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<List<Post>> getAll(@RequestParam(required = false) Integer start, @RequestParam(required = false) Integer limit){
		List<Post> result = new ArrayList<>();
		if(start != null) {
			result = postService.getAll(start, limit);
		}
		result = postService.getAll();
		return new ResponseEntity<List<Post>>(result, HttpStatus.OK);
	}
	
}
