package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.PostLike;
import com.lawencon.community.service.PostLikeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("post-likes")
public class PostLikeController {

	@Autowired
	private PostLikeService postLikeService;

	@PreAuthorize("hasAuthority('ROLMM')")
	@DeleteMapping("{id}")
	public ResponseEntity<ResponseDto> delete(@PathVariable("id") final String id) {
		final ResponseDto res = postLikeService.delete(id);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping
	public ResponseEntity<ResponseDto> insert(@RequestBody final PostLike data) {
		final ResponseDto res = postLikeService.insert(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
}
