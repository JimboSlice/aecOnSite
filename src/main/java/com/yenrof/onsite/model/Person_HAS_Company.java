package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

/**
 * The persistent class for the Person_HAS_Project database table.
 * 
 */
@Entity
@NamedQuery(name = "Person_HAS_Company.findAll", query = "SELECT p FROM Person_HAS_Company p")
public class Person_HAS_Company implements Serializable {
	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
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

	@Column(name = "companyId")
	private long companyId;

	@Column(name = "timeStamp")
	private Timestamp timeStamp;

}
