package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.PostPollingResponse;
import com.lawencon.community.service.PostPollingResponseService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("post-polling-responses")
public class PostPollingResponseController {

	@Autowired
	private PostPollingResponseService pollingResponseService;

	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping
	public ResponseEntity<ResponseDto> insert(@RequestBody final PostPollingResponse data) {
		final ResponseDto res = pollingResponseService.insert(data);
		return new ResponseEntity<ResponseDto>(res, HttpStatus.OK);
	}
	
}
