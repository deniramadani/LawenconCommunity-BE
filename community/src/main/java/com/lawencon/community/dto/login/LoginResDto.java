package com.lawencon.community.dto.login;

import lombok.Data;

@Data
public class LoginResDto {
	
	private String id;
	private String fullname;
	private String roleCode;
	private String userTypeCode;
	private String token;
	
}
