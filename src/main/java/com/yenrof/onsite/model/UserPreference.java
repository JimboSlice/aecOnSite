package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;


import java.util.Date;


/**
 * The persistent class for the UserPreference database table.
 * 
 */
@Entity
@NamedQuery(name="UserPreference.findAll", query="SELECT u FROM UserPreference u")
public class UserPreference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private long userPreferenceId;

	private byte[] companyLogo;

	private String companyName;

	private String consumerKey;

	private String consumerSecret;

	private byte deletedb;

	private String dropboxdirectory;

	private String dropboxid;

	private String dropboxpasswd;

	private String emaildefault;

	private String firstName;

	private String lastName;

	private String recordLimit;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	private String titleOfPosition;

	private byte trailVersion;

	//bi-directional one-to-one association to AppCustom
	@OneToOne(mappedBy="userPreference", cascade={CascadeType.ALL})
	@com.fasterxml.jackson.annotation.JsonManagedReference
	private AppCustom appCustom;

	//bi-directional one-to-one association to Report
	//@OneToOne(mappedBy="userPreference")
	//@JsonBackReference
	private Report report;

	public UserPreference() {
	}

	public long getUserPreferenceId() {
		return this.userPreferenceId;
	}

	public void setUserPreferenceId(long userPreferenceId) {
		this.userPreferenceId = userPreferenceId;
	}

	public byte[] getCompanyLogo() {
		return this.companyLogo;
	}

	public void setCompanyLogo(byte[] companyLogo) {
		this.companyLogo = companyLogo;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getConsumerKey() {
		return this.consumerKey;
	}

	public void setConsumerKey(String consumerKey) {
		this.consumerKey = consumerKey;
	}

	public String getConsumerSecret() {
		return this.consumerSecret;
	}

	public void setConsumerSecret(String consumerSecret) {
		this.consumerSecret = consumerSecret;
	}

	public byte getDeletedb() {
		return this.deletedb;
	}

	public void setDeletedb(byte deletedb) {
		this.deletedb = deletedb;
	}

	public String getDropboxdirectory() {
		return this.dropboxdirectory;
	}

	public void setDropboxdirectory(String dropboxdirectory) {
		this.dropboxdirectory = dropboxdirectory;
	}

	public String getDropboxid() {
		return this.dropboxid;
	}

	public void setDropboxid(String dropboxid) {
		this.dropboxid = dropboxid;
	}

	public String getDropboxpasswd() {
		return this.dropboxpasswd;
	}

	public void setDropboxpasswd(String dropboxpasswd) {
		this.dropboxpasswd = dropboxpasswd;
	}

	public String getEmaildefault() {
		return this.emaildefault;
	}

	public void setEmaildefault(String emaildefault) {
		this.emaildefault = emaildefault;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getRecordLimit() {
		return this.recordLimit;
	}

	public void setRecordLimit(String recordLimit) {
		this.recordLimit = recordLimit;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getTitleOfPosition() {
		return this.titleOfPosition;
	}

	public void setTitleOfPosition(String titleOfPosition) {
		this.titleOfPosition = titleOfPosition;
	}

	public byte getTrailVersion() {
		return this.trailVersion;
	}

	public void setTrailVersion(byte trailVersion) {
		this.trailVersion = trailVersion;
	}

	public AppCustom getAppCustom() {
		return this.appCustom;
	}

	public void setAppCustom(AppCustom appCustom) {
		this.appCustom = appCustom;
	}

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

}