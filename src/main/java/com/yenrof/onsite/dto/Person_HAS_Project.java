package com.yenrof.onsite.dto;

import java.io.Serializable;

import javax.persistence.*;

import java.util.Date;

/**
 * The persistent class for the Person_HAS_Project database table.
 * 
 */
public class Person_HAS_Project implements Serializable {
	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "personId")
	private long personId;

	@Column(name = "projectId")
	private long projectId;

	@Column(name = "timeStamp")
 	private Date timeStamp;

}
