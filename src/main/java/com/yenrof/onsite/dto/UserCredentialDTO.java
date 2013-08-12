package com.yenrof.onsite.dto;

import java.io.Serializable;
/**
 * The  DTO class for the UserCredential
 * 
 */
public class UserCredentialDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private String password;


	public UserCredentialDTO() {
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