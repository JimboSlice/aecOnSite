 package com.yenrof.onsite.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;



/**
 * The DTO class for the Person database table.
 * 
 */
public class PersonDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long personId;

	private String email;

	private String subscriptionType;
	private String password;
	
	private String role;
	
	private Timestamp timeStamp;

	
	
	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	// bi-directional many-to-many association to Projects - DONT NEED IN JSON
	// FORMAT
	@JsonIgnore
	private Set<ProjectDTO> projects = new LinkedHashSet<ProjectDTO>(0);
	// bi-directional many-to-many association to Company - DONT NEED IN JSON
	// FORMAT
	@JsonIgnore
	private Set<CompanyDTO> companies = new LinkedHashSet<CompanyDTO>(0);

	public Set<CompanyDTO> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<CompanyDTO> companies) {
		this.companies = companies;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(Set<ProjectDTO> projects) {
		this.projects = projects;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(String subscriptionType) {
		this.subscriptionType = subscriptionType;
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
