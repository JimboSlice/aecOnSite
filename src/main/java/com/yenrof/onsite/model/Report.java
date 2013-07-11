package com.yenrof.onsite.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Report database table.
 * 
 */
@Entity
@NamedQuery(name="Report.findAll", query="SELECT r FROM Report r")
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String reportId;

	private String constructionphase;

	private int peopleOnSite;

	private String rname;

	private String rtype;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	private byte[] voiceData;

	private byte[] weatherData;

	//bi-directional many-to-one association to Area
	@OneToMany(mappedBy="report")
	private List<Area> areas;

	//bi-directional many-to-one association to Project
	@ManyToOne
	@JoinColumn(name="Project_projectId")
	private Project project;

	//bi-directional one-to-one association to UserPreference
	@OneToOne
	@JoinColumn(name="UserPreference_userPreferenceId")
	private UserPreference userPreference;

	public Report() {
	}

	public String getReportId() {
		return this.reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getConstructionphase() {
		return this.constructionphase;
	}

	public void setConstructionphase(String constructionphase) {
		this.constructionphase = constructionphase;
	}

	public int getPeopleOnSite() {
		return this.peopleOnSite;
	}

	public void setPeopleOnSite(int peopleOnSite) {
		this.peopleOnSite = peopleOnSite;
	}

	public String getRname() {
		return this.rname;
	}

	public void setRname(String rname) {
		this.rname = rname;
	}

	public String getRtype() {
		return this.rtype;
	}

	public void setRtype(String rtype) {
		this.rtype = rtype;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public byte[] getVoiceData() {
		return this.voiceData;
	}

	public void setVoiceData(byte[] voiceData) {
		this.voiceData = voiceData;
	}

	public byte[] getWeatherData() {
		return this.weatherData;
	}

	public void setWeatherData(byte[] weatherData) {
		this.weatherData = weatherData;
	}

	public List<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}

	public Area addArea(Area area) {
		getAreas().add(area);
		area.setReport(this);

		return area;
	}

	public Area removeArea(Area area) {
		getAreas().remove(area);
		area.setReport(null);

		return area;
	}

	public Project getProject() {
		return this.project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public UserPreference getUserPreference() {
		return this.userPreference;
	}

	public void setUserPreference(UserPreference userPreference) {
		this.userPreference = userPreference;
	}

}