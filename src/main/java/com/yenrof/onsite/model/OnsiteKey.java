package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
/**
 * The entity class for the UserCredentials.
 * 
 */
@Entity
@NamedQuery(name="OnsiteKey.findAll", query="SELECT o FROM OnsiteKey o")

public class OnsiteKey implements Serializable {
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long personId;

	private long companyId;

	public OnsiteKey() {
	}

}