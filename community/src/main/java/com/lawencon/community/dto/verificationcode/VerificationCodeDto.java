package com.lawencon.community.dto.verificationcode;

import lombok.Data;

@Data
public class VerificationCodeDto {
	private String email;
	private String code;
}
