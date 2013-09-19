package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.codehaus.jackson.annotate.JsonManagedReference;
import org.hibernate.validator.constraints.NotEmpty;

 import com.yenrof.onsite.dto.PersonDTO;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * The persistent class for the Company database table.
 * 
 */
@Entity
@NamedQuery(name = "Company.findAll", query = "SELECT c FROM Company c")
public class Company implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long companyId;

	@NotNull
	@NotEmpty
	@Column(name = "name")
	private String name;

	private String address;

	private String city;

	private String state;

	private String zipcode;

	private String email;

	private String phone;

	// bi-directional many-to-many association to persons with association
	// table
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinTable(name = "Person_HAS_Company", joinColumns = { @JoinColumn(name = "companyId", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "personId", nullable = false, updatable = false) })
	private Set<Person> persons = new LinkedHashSet<Person>(0);

	public Set<Person> getPersons() {
		return persons;
	}

	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}

	// bi-directional many-to-one association to Project
	@JsonManagedReference("projectref")
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "company")
	private Set<Project> projects = new LinkedHashSet<Project>(0);

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
		this.projects = projects;
	}

	private Timestamp timeStamp;

	public Company() {
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

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Person addPerson(Person person) {
		this.getPersons().add(person);
		person.addCompany(this);
		return person;
	}

	public PersonDTO addPerson(PersonDTO person) {
		// JKF convert to person then add
		return person;
	}
}
