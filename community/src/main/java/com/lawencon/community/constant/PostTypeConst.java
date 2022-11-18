package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum PostTypeConst {
	PREMIUM("PSTPM", "Premium"), BASIC("PSTBS", "Basic"), POLLING("PSTPL", "Basic");

	private final String postTypeCodeEnum;
	private final String postTypeNameEnum;
	
	PostTypeConst(final String postTypeCodeEnum, final String postTypeNameEnum) {
		this.postTypeCodeEnum = postTypeCodeEnum;
		this.postTypeNameEnum = postTypeNameEnum;
	}
}
