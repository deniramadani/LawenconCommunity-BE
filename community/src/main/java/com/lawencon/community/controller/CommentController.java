package com.lawencon.community.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.Comment;
import com.lawencon.community.service.CommentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("comments")
@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@GetMapping("/post/{id}")
	public ResponseEntity<List<Comment>> getByAll(@PathVariable String id) {
		final List<Comment> result = commentService.getAllByPost(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
