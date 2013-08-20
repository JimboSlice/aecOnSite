package com.yenrof.onsite.request;

import java.io.Serializable;

import com.yenrof.onsite.dto.AreaDTO;

/**
 * The DTO request class for the Report database table.
 * 
 */
public class AddAreaRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long personId;
	private long projectId;
	private long reportId;
	private AreaDTO area = new AreaDTO();
	public AreaDTO getArea() {
		return area;
	}
	public long getReportId() {
		return reportId;
	}
	public void setReportId(long reportId) {
		this.reportId = reportId;
	}
	public void setArea(AreaDTO area) {
		this.area = area;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public long getProjectId() {
		return projectId;
	}
	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	}
