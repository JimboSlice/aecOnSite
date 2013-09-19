package com.yenrof.onsite.dto;

import java.io.Serializable;

import java.sql.Timestamp;
import java.util.LinkedHashSet;
import java.util.Set;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * The DTO class for the Project database table.
 * 
 */
public class ProjectDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private long projectId;

	private String address;

	private String city;

	private String country;

	private String countryCode;

	private String county;

	private String displayPic;

	private float latcoord;

	private float longcoord;

	private String neighborhood;
	private String projectName;

	private String projectNumber;

	private String state;

	private String subAddress;
	
 	private Timestamp timeStamp;
	private String uniqueRoomName;

	private String zipcode;

	@JsonBackReference("projectref")
	private CompanyDTO company;

	@JsonManagedReference("reportref")
	private Set<ReportDTO> reports;

	private Set<PersonDTO> persons = new LinkedHashSet<PersonDTO>(0);

	public Set<PersonDTO> getPersons() {
		return persons;
	}

	public ProjectDTO() {
	}

	public void setPersons(Set<PersonDTO> persons) {
		this.persons = persons;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public long getProjectId() {
		return this.projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCounty() {
		return this.county;
	}

	public void setCounty(String county) {
		this.county = county;
	}

	public String getDisplayPic() {
		return this.displayPic;
	}

	public void setDisplayPic(String displayPic) {
		this.displayPic = displayPic;
	}

	public float getLatcoord() {
		return this.latcoord;
	}

	public void setLatcoord(float latcoord) {
		this.latcoord = latcoord;
	}

	public float getLongcoord() {
		return this.longcoord;
	}

	public void setLongcoord(float longcoord) {
		this.longcoord = longcoord;
	}

	public String getNeighborhood() {
		return this.neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getProjectName() {
		return this.projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getProjectNumber() {
		return this.projectNumber;
	}

	public void setProjectNumber(String projectNumber) {
		this.projectNumber = projectNumber;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSubAddress() {
		return this.subAddress;
	}

	public void setSubAddress(String subAddress) {
		this.subAddress = subAddress;
	}

	public String getUniqueRoomName() {
		return this.uniqueRoomName;
	}

	public void setUniqueRoomName(String uniqueRoomName) {
		this.uniqueRoomName = uniqueRoomName;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Set<ReportDTO> getReports() {
		return this.reports;
	}

	public void setReports(Set<ReportDTO> reports) {
		this.reports = reports;
	}

	public ReportDTO addReport(ReportDTO report) {
		getReports().add(report);
		report.setProject(this);

		return report;
	}

	public ReportDTO removeReport(ReportDTO report) {
		getReports().remove(report);
		report.setProject(null);

		return report;
	}

	public PersonDTO addPerson(PersonDTO person) {
		this.getPersons().add(person);
		// person.addProject(this);
		return person;
	}

	public PersonDTO removeInspector(PersonDTO person) {
		getPersons().remove(person);
		return person;
	}

	public CompanyDTO getCompany() {
		return company;
	}

	public void setCompany(CompanyDTO company) {
		this.company = company;
	}

	public Timestamp getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

}
