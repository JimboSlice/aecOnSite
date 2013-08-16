package com.yenrof.onsite.request;

import java.io.Serializable;

import com.yenrof.onsite.dto.PersonDTO;

/**
 * The DTO class for the Company database table.
 * 
 */
public class AddPersonRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long companyId;


	private PersonDTO person = new PersonDTO();


	public long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}


	public PersonDTO getPerson() {
		return person;
	}


	public void setPerson(PersonDTO person) {
		this.person = person;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
