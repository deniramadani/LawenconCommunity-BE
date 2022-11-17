package com.lawencon.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.dto.verificationcode.VerificationCodeDto;
import com.lawencon.community.model.User;
import com.lawencon.community.service.VerificationCodeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("verification-code")
public class VerificationCodeController {
	@Autowired
	private VerificationCodeService verificationCodeService;

	@PostMapping("/generate")
	public ResponseEntity<ResponseDto> generateCode(@RequestBody User data) {
		final ResponseDto responseDto =verificationCodeService.generateVerificationCode(data.getEmail());
		 
		
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
	@PostMapping("/validate")
	public ResponseEntity<ResponseDto> validateCode(@RequestBody VerificationCodeDto data) {
		final ResponseDto responseDto = verificationCodeService.validateCode(data);
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
}
