package com.lawencon.community.dto.exception;

import lombok.Data;

@Data
public class ExceptionDto<T> {
	
	T message;

	public T getMessage() {
		return message;
	}

	public void setMessage(T message) {
		this.message = message;
	}
	
}
