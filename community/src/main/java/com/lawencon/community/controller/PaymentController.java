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

import com.lawencon.community.constant.ProductTypeConst;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.model.Payment;
import com.lawencon.community.service.PaymentService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("payments")
public class PaymentController {
	
	@Autowired
	private PaymentService paymentService;
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("events")
	public ResponseEntity<ResponseDto> eventPayment(@RequestBody final Payment data){
		final ResponseDto result = paymentService.insert(data, ProductTypeConst.EVENT.getProductTypeCodeEnum());
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("courses")
	public ResponseEntity<ResponseDto> coursePayment(@RequestBody final Payment data){
		final ResponseDto result = paymentService.insert(data, ProductTypeConst.COURSE.getProductTypeCodeEnum());
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLMM')")
	@PostMapping("subscriptions")
	public ResponseEntity<ResponseDto> subscribePayment(@RequestBody final Payment data){
		final ResponseDto result = paymentService.insert(data, ProductTypeConst.SUBSCRIBE.getProductTypeCodeEnum());
		return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@PutMapping("valid/{id}")
	public ResponseEntity<ResponseDto> paymentAccepted(@PathVariable("id") final String id) {
		final ResponseDto result = paymentService.paymentAccepted(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAuthority('ROLAM')")
	@PutMapping("invalid/{id}")
	public ResponseEntity<ResponseDto> paymentRejected(@PathVariable("id") final String id) {
		final ResponseDto result = paymentService.paymentRejected(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping("{id}")
	public ResponseEntity<Payment> getById(@PathVariable("id") final String id){
		final Payment result = paymentService.getById(id);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLSA', 'ROLAM', 'ROLMM')")
	@GetMapping
	public ResponseEntity<List<Payment>> getAll(@RequestParam(required = false) final Integer start,
			@RequestParam(required = false) final Integer limit){
		final List<Payment> result = paymentService.getAll(start, limit);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
}
