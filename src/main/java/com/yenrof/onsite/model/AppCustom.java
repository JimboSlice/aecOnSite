package com.yenrof.onsite.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the AppCustom database table.
 * 
 */
@Entity
@NamedQuery(name="AppCustom.findAll", query="SELECT a FROM AppCustom a")
public class AppCustom implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String appCustomId;

	@Column(name="I1Title")
	private String i1Title;

	@Column(name="I2Title")
	private String i2Title;

	@Column(name="I3Title")
	private String i3Title;

	@Column(name="I4Title")
	private String i4Title;

	private String theTitle;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	//bi-directional one-to-one association to UserPreference
	@OneToOne
	@JoinColumn(name="UserPreference_userPreferenceId")
	private UserPreference userPreference;

	public AppCustom() {
	}

	public String getAppCustomId() {
		return this.appCustomId;
	}

	public void setAppCustomId(String appCustomId) {
		this.appCustomId = appCustomId;
	}

	public String getI1Title() {
		return this.i1Title;
	}

	public void setI1Title(String i1Title) {
		this.i1Title = i1Title;
	}

	public String getI2Title() {
		return this.i2Title;
	}

	public void setI2Title(String i2Title) {
		this.i2Title = i2Title;
	}

	public String getI3Title() {
		return this.i3Title;
	}

	public void setI3Title(String i3Title) {
		this.i3Title = i3Title;
	}

	public String getI4Title() {
		return this.i4Title;
	}

	public void setI4Title(String i4Title) {
		this.i4Title = i4Title;
	}

	public String getTheTitle() {
		return this.theTitle;
	}

	public void setTheTitle(String theTitle) {
		this.theTitle = theTitle;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public UserPreference getUserPreference() {
		return this.userPreference;
	}

	public void setUserPreference(UserPreference userPreference) {
		this.userPreference = userPreference;
	}

}