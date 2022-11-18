package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum ProductTypeConst {
	
	EVENT("PRODE", "Event"), COURSE("PRODC", "Course");

	private final String productTypeCodeEnum;
	private final String productTypeNameEnum;
	
	ProductTypeConst(final String productTypeCodeEnum, final String productTypeNameEnum) {
		this.productTypeCodeEnum = productTypeCodeEnum;
		this.productTypeNameEnum = productTypeNameEnum;
	}
	
}
