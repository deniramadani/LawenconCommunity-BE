package com.lawencon.community.controller;

import java.util.Base64;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.File;
import com.lawencon.community.service.FileService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("files")
public class FileController {
	
	@Autowired private FileService fileService;
	
	@GetMapping("download/{id}")
    public ResponseEntity<?> download(@PathVariable("id") final String id) {
        final Optional<File> file = fileService.getById(id);
        final byte[] fileBytes = Base64.getDecoder().decode(file.get().getFileEncode());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=attachment." + file.get().getFileExtensions())
                .body(fileBytes);
    }
	
}
