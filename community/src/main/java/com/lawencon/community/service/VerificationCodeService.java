package com.lawencon.community.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.dto.verificationcode.VerificationCodeDto;
import com.lawencon.community.util.GenerateCodeUtil;
import com.lawencon.util.MailUtil;
import com.lawencon.util.VerificationCodeUtil;

@Service
public class VerificationCodeService extends BaseCoreService {
	@Autowired
	private MailUtil mailUtil;

	@Autowired
	private VerificationCodeUtil verificationCodeUtil;

	public ResponseDto generateVerificationCode(String email) {
		final Map<String, Object> template = new HashMap<>();
		final String code = GenerateCodeUtil.generateCode();
		template.put("email", email);
		template.put("code", code);
		System.err.println(email);
		final String subject = "Verification Code for Activating Account";
		try {
			mailUtil.sendMessageFreeMarker(email, subject, template, "verification-code-template.ftl");
		} catch (Exception e) {
			e.printStackTrace();
		}
		verificationCodeUtil.addVerificationCode(email, code);
		ResponseDto responseDto = new ResponseDto();
		responseDto.setMessage("Verification Code Sent to your email. Please Check your Email.");
		return responseDto;
	}
	
	public ResponseDto validateCode(VerificationCodeDto data) {
		final ResponseDto responseDto = new ResponseDto();
			verificationCodeUtil.validateVerificationCode(data.getEmail(), data.getCode());
			responseDto.setMessage("Verification Success");
		return responseDto;
		
	}
}
