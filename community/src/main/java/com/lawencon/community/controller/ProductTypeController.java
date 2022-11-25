package com.lawencon.community.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.model.ProductType;
import com.lawencon.community.service.ProductTypeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("product-types")
public class ProductTypeController {
	
	@Autowired
	private ProductTypeService productTypeService;
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping
	public ResponseEntity<List<ProductType>> getAllEC(){
		List<ProductType> result = new ArrayList<>(); 
		result = productTypeService.getAllEC();						
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
