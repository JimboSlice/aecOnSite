package com.yenrof.onsite.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

/**
 * The DTO class for the Report database table.
 * 
 */
public class ReportDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long reportId;

	private String constructionphase;

	private int peopleOnSite;

	private String rname;

	private String rtype;

	private Timestamp timeStamp;
	@JsonBackReference("reportref")
	private ProjectDTO project;
	@JsonManagedReference
	private Set<AreaDTO> areas;

	public ReportDTO() {
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

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Set<AreaDTO> getAreas() {
		return this.areas;
	}

	public void setAreas(Set<AreaDTO> areas) {
		this.areas = areas;
	}

	public AreaDTO addArea(AreaDTO area) {
		getAreas().add(area);
		area.setReport(this);

		return area;
	}

	public AreaDTO removeArea(AreaDTO area) {
		getAreas().remove(area);
		area.setReport(null);

		return area;
	}

	public ProjectDTO getProject() {
		return this.project;
	}

	public void setProject(ProjectDTO project) {
		this.project = project;
	}

}