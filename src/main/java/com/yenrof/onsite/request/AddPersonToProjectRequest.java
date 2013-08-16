package com.yenrof.onsite.request;

import java.io.Serializable;

import com.yenrof.onsite.dto.PersonDTO;

/**
 * The DTO request class for the Project database table.
 * 
 */
public class AddPersonToProjectRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long companyId;
	private long projectId;


	private PersonDTO person = new PersonDTO();


	public long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public long getProjectId() {
		return projectId;
	}


	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}


	public PersonDTO getPerson() {
		return person;
	}


	public void setPerson(PersonDTO person) {
		this.person = person;
	}


}
