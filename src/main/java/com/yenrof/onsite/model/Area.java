package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;
import org.codehaus.jackson.annotate.JsonBackReference;
import org.codehaus.jackson.annotate.JsonManagedReference;


import java.sql.Timestamp;
import java.util.Set;

/**
 * The persistent class for the Area database table.
 * 
 */
@Entity
@NamedQuery(name = "Area.findAll", query = "SELECT a FROM Area a")
public class Area implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long areaId;

	private String comment;

	private String name;

	private String number;

	private Timestamp timeStamp;

	// bi-directional many-to-one association to Report
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name = "Report_reportId", referencedColumnName = "reportId")
	private Report report;

	// bi-directional many-to-one association to Asset
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "area")
	// @IndexColumn(name="assetId")
	// @LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private Set<Asset> assets;

	// bi-directional many-to-one association to Note
	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
			CascadeType.MERGE }, mappedBy = "area")
	// @IndexColumn(name="noteId")
	// @LazyCollection(LazyCollectionOption.FALSE)
	@JsonManagedReference
	private Set<Note> notes;

	public Area() {
	}

	public long getAreaId() {
		return this.areaId;
	}

	public void setAreaId(long areaId) {
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

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public Report getReport() {
		return this.report;
	}

	public void setReport(Report report) {
		this.report = report;
	}

	public Set<Asset> getAssets() {
		return this.assets;
	}

	public void setAssets(Set<Asset> assets) {
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

	public Set<Note> getNotes() {
		return this.notes;
	}

	public void setNotes(Set<Note> notes) {
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