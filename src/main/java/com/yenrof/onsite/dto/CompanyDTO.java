package com.yenrof.onsite.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * The DTO class for the Company database table.
 * 
 */
public class CompanyDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private long companyId;

	private String name;

	private String address;

	private String city;

	private String state;

	private String zipcode;

	private String email;

	private String phone;

	private Set<PersonDTO> persons = new LinkedHashSet<PersonDTO>(0);

	public Set<PersonDTO> getPersons() {
		return persons;
	}

	public void setPersons(Set<PersonDTO> persons) {
		this.persons = persons;
	}
	@JsonManagedReference("projectref")
	private Set<ProjectDTO> projects = new LinkedHashSet<ProjectDTO>(0);

	public Set<ProjectDTO> getProjects() {
		return projects;
	}

	public void setProjects(Set<ProjectDTO> projects) {
		this.projects = projects;
	}

	private Date timeStamp;

	public CompanyDTO() {
	}

	public long getCompanyId() {
		return companyId;
	}

	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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
	
	public PersonDTO addPerson(PersonDTO person) {
		this.getPersons().add(person);
		//person.addCompany(this);
		return person;
	}
}
