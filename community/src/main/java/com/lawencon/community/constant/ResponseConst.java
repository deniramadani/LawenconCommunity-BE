package com.lawencon.community.constant;

import lombok.Getter;

@Getter
public enum ResponseConst {
	
	CREATED("Created"), UPDATED("Updated"), DELETED("Deleted");
	
	private final String response;
	
	ResponseConst(final String response) {
		this.response = response;
	}

}
