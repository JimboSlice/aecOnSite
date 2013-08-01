package com.yenrof.onsite.model;

import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;



//import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

//import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * The persistent class for the Person database table.
 * 
 */
@Entity
@NamedQuery(name = "Person.findAll", query = "SELECT a FROM Person  a")
public class Person implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long personId;

	@NotNull
	@NotEmpty
	@Email(message = "Invalid format")
	@Column(name = "email")
	private String email;

	@NotNull
	@NotEmpty
	@Column(name = "username")
	private String username;

	@Column(name = "subscriptionType")
	private String subscriptionType;

	@NotNull
	@NotEmpty
	@Column(name = "password")
	private String password;

	@Column(name = "role")
	private String role;

	// bi-directional many-to-many association to Projects - DONT NEED IN JSON
	// FORMAT
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "persons")
	private Set<Project> projects = new LinkedHashSet<Project>(0);

	// bi-directional many-to-many association to Company - DONT NEED IN JSON
	// FORMAT
	@JsonIgnore
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "persons")
	private Set<Company> companies = new LinkedHashSet<Company>(0);

	public Set<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(Set<Company> companies) {
		this.companies = companies;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Set<Project> getProjects() {
		return projects;
	}

	public void setProjects(Set<Project> projects) {
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public Project addProject(Project project) {
		this.getProjects().add(project);
		return project;
	}

	public Project removeProject(Project project) {
		getProjects().remove(project);
		return project;
	}
	
	public Company addCompany(Company company) {
		this.getCompanies().add(company);
		return company;
	}

}
