package com.lawencon.community.service;

import org.springframework.stereotype.Service;

import com.lawencon.base.BaseCoreService;
import com.lawencon.community.dto.response.ResponseDto;
import com.lawencon.community.util.GenerateCodeUtil;
@Service
public class VerificationCodeService extends BaseCoreService {
	public ResponseDto generateVerificationCode(String email) {
		final String code = GenerateCodeUtil.generateCode();
		
		ResponseDto responseDto = new ResponseDto();
		return responseDto;
	}
}
