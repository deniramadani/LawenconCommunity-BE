package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum UserTypeConst {
	
	PREMIUM("UTCPM", "Premium"), BASIC("UTCBS", "Basic");

	private final String userTypeCodeEnum;
	private final String userTypeNameEnum;
	
	UserTypeConst(final String userTypeCodeEnum, final String userTypeNameEnum) {
		this.userTypeCodeEnum = userTypeCodeEnum;
		this.userTypeNameEnum = userTypeNameEnum;
	}
	
}
