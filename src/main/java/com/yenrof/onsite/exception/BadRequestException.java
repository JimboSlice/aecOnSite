package com.yenrof.onsite.exception;

public class BadRequestException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2375479178807585985L;

	public BadRequestException(String s) {
		super(s);
	}
}
