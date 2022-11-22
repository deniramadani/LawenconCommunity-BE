package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum ResponseConst {
	
	CREATED("Created"), UPDATED("Updated"), DELETED("Deleted"), FAILED("Failed"),
	PAYMENT_ACCEPTED("Payment Approved"), PAYMENT_REJECTED("Payment Refused!"),
	USER_NOT_FOUND("User not Found!");
	
	private final String response;
	
	ResponseConst(final String response) {
		this.response = response;
	}

}
