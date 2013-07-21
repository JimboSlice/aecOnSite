package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;


/**
 * The persistent class for the Company database table.
 * 
 */
@Entity
@NamedQuery(name="Company.findAll", query="SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String companyId;

	private String name;

	private String address;

	private String city;

	private String state;

	private String zipcode;
	
	private String email;

	private String phone;
	

	//bi-directional many-to-one association to Project
	@JsonManagedReference("projectref")
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.ALL, mappedBy="company")
	private Set<Project> projects = new LinkedHashSet<Project>(0);

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}


	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	

	public Company() {
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
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

	
}
