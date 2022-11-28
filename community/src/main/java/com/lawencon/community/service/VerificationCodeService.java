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

	public ResponseDto generateVerificationCode(final String email) {
		final Map<String, Object> template = new HashMap<>();
		final String code = GenerateCodeUtil.generateCode();
		final ResponseDto responseDto = new ResponseDto();
		template.put("email", email);
		template.put("code", code);
		final String subject = "Verification Code for Activating Account";
		try {
			mailUtil.sendMessageFreeMarker(email, subject, template, "verification-code-template.ftl");
		} catch (Exception e) {
			responseDto.setMessage("Verification Code Failed to Send.");
			e.printStackTrace();
		}
		verificationCodeUtil.addVerificationCode(email, code);
		responseDto.setMessage("Verification Code Sent to your email. Please Check your Email.");
		return responseDto;
	}
	
	public ResponseDto validateCode(final VerificationCodeDto data) {
		final ResponseDto responseDto = new ResponseDto();
			verificationCodeUtil.validateVerificationCode(data.getEmail(), data.getCode());
			responseDto.setMessage("Verification Success");
		return responseDto;
	}
	
}
