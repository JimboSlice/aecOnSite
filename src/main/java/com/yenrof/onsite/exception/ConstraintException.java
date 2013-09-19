package com.yenrof.onsite.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;

public class ConstraintException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2375479178807585985L;
	private Set<ConstraintViolation<?>> constraints;
	public ConstraintException(Set<ConstraintViolation<?>> set) {
		this.setConstraints(set);
	}
	public Set<ConstraintViolation<?>> getConstraints() {
		return constraints;
	}
	public void setConstraints(Set<ConstraintViolation<?>> constraints) {
		this.constraints = constraints;
	}
}
