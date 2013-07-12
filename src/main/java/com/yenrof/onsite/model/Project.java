package com.yenrof.onsite.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import org.codehaus.jackson.annotate.JsonManagedReference;


/**
 * The persistent class for the Project database table.
 * 
 */
@Entity
@NamedQuery(name="Project.findAll", query="SELECT p FROM Project p")
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String projectId;

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

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	private String uniqueRoomName;

	private byte[] voiceData;

	private String zipcode;

	//bi-directional many-to-one association to Report
	@JsonManagedReference
	@OneToMany(cascade=CascadeType.ALL, mappedBy="project")
	private List<Report> reports;

	public Project() {
	}

	public String getProjectId() {
		return this.projectId;
	}

	public void setProjectId(String projectId) {
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

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getUniqueRoomName() {
		return this.uniqueRoomName;
	}

	public void setUniqueRoomName(String uniqueRoomName) {
		this.uniqueRoomName = uniqueRoomName;
	}

	public byte[] getVoiceData() {
		return this.voiceData;
	}

	public void setVoiceData(byte[] voiceData) {
		this.voiceData = voiceData;
	}

	public String getZipcode() {
		return this.zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public List<Report> getReports() {
		return this.reports;
	}

	public void setReports(List<Report> reports) {
		this.reports = reports;
	}

	public Report addReport(Report report) {
		getReports().add(report);
		report.setProject(this);

		return report;
	}

	public Report removeReport(Report report) {
		getReports().remove(report);
		report.setProject(null);

		return report;
	}

}