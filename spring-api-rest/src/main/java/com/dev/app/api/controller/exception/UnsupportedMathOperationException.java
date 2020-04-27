package com.dev.app.api.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UnsupportedMathOperationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8901056969636804266L;

	public UnsupportedMathOperationException(String exception) {
		super(exception);
	}
}
