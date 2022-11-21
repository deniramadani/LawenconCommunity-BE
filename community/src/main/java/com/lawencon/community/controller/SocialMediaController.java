package com.lawencon.community.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.SocialMedia;
import com.lawencon.community.service.SocialMediaService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("social-medias")
@PreAuthorize("hasAuthority('ROLSA', 'ROLAM', 'ROLMM')")
public class SocialMediaController {

	@Autowired
	private SocialMediaService socialMediaService;

	@GetMapping
	public ResponseEntity<List<SocialMedia>> getAll() {
		final List<SocialMedia> result = socialMediaService.getAll();
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
