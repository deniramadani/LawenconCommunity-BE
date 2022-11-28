package com.lawencon.community.controller;

import java.util.Optional;

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
import com.lawencon.community.service.UserService;
import com.lawencon.community.service.VerificationCodeService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("verification-code")
public class VerificationCodeController {
	
	@Autowired
	private VerificationCodeService verificationCodeService;
	@Autowired
	private UserService userService;
	
	@PostMapping("/generate")
	public ResponseEntity<ResponseDto> generateCode(@RequestBody final User data) {
		ResponseDto response = new ResponseDto();
		final Optional<User> result = userService.getByEmail(data.getEmail());
		if(result.isEmpty()) {
			response = verificationCodeService.generateVerificationCode(data.getEmail());
			return new ResponseEntity<ResponseDto>(response, HttpStatus.OK);			
		} else {
			response.setMessage("Email Already Exist!");
			return new ResponseEntity<ResponseDto>(response, HttpStatus.CONFLICT);	
		}
	}
	
	@PostMapping("/validate")
	public ResponseEntity<ResponseDto> validateCode(@RequestBody final VerificationCodeDto data) {
		final ResponseDto responseDto = verificationCodeService.validateCode(data);
		return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
	}
	
}
