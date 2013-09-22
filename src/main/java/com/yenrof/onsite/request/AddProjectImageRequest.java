package com.yenrof.onsite.request;

import java.io.Serializable;


/**
 * The DTO request class for adding project images.
 * 
 */
public class AddProjectImageRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long personId;
	private long projectId;
	private long companyId;
	private String image;
	
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
	public long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	}
