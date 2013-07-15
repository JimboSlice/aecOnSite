package com.yenrof.onsite.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ReferenceData database table.
 * 
 */
@Entity
@NamedQuery(name="ReferenceData.findAll", query="SELECT r FROM ReferenceData r")
public class ReferenceData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long referenceDataId;

	private String countries;

	public ReferenceData() {
	}

	public long getReferenceDataId() {
		return this.referenceDataId;
	}

	public void setReferenceDataId(long referenceDataId) {
		this.referenceDataId = referenceDataId;
	}

	public String getCountries() {
		return this.countries;
	}

	public void setCountries(String countries) {
		this.countries = countries;
	}

}