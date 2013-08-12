package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;


import org.hibernate.validator.constraints.NotEmpty;



/**
 * The  class for the UserCredential
 * 
 */
public class UserCredential implements Serializable {
	private static final long serialVersionUID = 1L;
	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String password;


	public UserCredential() {
	}


	public String getUserName() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}