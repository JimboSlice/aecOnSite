package com.yenrof.onsite.request;

import java.io.Serializable;

import com.yenrof.onsite.dto.ProjectDTO;

/**
 * The DTO request class for the Project database table.
 * 
 */
public class AddProjectRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long companyId;


	private ProjectDTO project = new ProjectDTO();


	public long getCompanyId() {
		return companyId;
	}


	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}


	public ProjectDTO getProject() {
		return project;
	}


	public void setProject(ProjectDTO project) {
		this.project = project;
	}

}
