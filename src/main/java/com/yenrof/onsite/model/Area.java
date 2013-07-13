package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Area database table.
 * 
 */
@Entity
@NamedQuery(name="Area.findAll", query="SELECT a FROM Area a")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String areaId;

	private String comment;

	private String name;

	private String number;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	private byte[] voiceData;

	//bi-directional many-to-one association to Report
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="Report_reportId", referencedColumnName="reportId")
	private Report report;

	//bi-directional many-to-one association to Asset
	@OneToMany(cascade = CascadeType.ALL,mappedBy="area")
	//@JsonManagedReference
	private List<Asset> assets;

	//bi-directional many-to-one association to Note
	@OneToMany(mappedBy="area")
	//@JsonManagedReference
	private List<Note> notes;

	public Area() {
	}

	public String getAreaId() {
		return this.areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
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

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public List<Asset> getAssets() {
		return this.assets;
	}

	public void setAssets(List<Asset> assets) {
		this.assets = assets;
	}

	public Asset addAsset(Asset asset) {
		getAssets().add(asset);
		asset.setArea(this);

		return asset;
	}

	public Asset removeAsset(Asset asset) {
		getAssets().remove(asset);
		asset.setArea(null);

		return asset;
	}

	public List<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}

	public Note addNote(Note note) {
		getNotes().add(note);
		note.setArea(this);

		return note;
	}

	public Note removeNote(Note note) {
		getNotes().remove(note);
		note.setArea(null);

		return note;
	}

}