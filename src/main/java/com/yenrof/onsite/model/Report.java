package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.sql.Timestamp;
import java.util.Set;

/**
 * The persistent class for the Report database table.
 * 
 */
@Entity
@NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r")
public class Report implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long reportId;

	private String constructionphase;

	private int peopleOnSite;

	private String rname;

	private String rtype;

	private Timestamp timeStamp;

	// bi-directional many-to-one association to Project
	@JsonBackReference("reportref")
	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE })
	@JoinColumn(name = "Project_projectId")
	private Project project;

	// bi-directional many-to-one association to Area
	@JsonManagedReference
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "report")
	private Set<Area> areas;

	public Report() {
	}

	public long getReportId() {
		return this.reportId;
	}

	public void setReportId(long reportId) {
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

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Set<Area> getAreas() {
		return this.areas;
	}

	public void setAreas(Set<Area> areas) {
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

}