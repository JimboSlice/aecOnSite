package com.yenrof.onsite.request;

import java.io.Serializable;

import com.yenrof.onsite.dto.ReportDTO;

/**
 * The DTO request class for the Report database table.
 * 
 */
public class AddReportRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long personId;
	private long projectId;
	private ReportDTO report = new ReportDTO();
	public ReportDTO getReport() {
		return report;
	}
	public void setReport(ReportDTO report) {
		this.report = report;
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
