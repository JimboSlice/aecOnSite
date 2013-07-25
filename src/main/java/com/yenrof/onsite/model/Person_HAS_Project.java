package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the Person_HAS_Project database table.
 * 
 */
@Entity
@NamedQuery(name = "Person_HAS_Project.findAll", query = "SELECT p FROM Person_HAS_Project p")
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

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
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
	private Timestamp timeStamp;

}
