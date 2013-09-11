package com.yenrof.onsite.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * The DTO class for the Area database table.
 * 
 */
public class AreaDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long areaId;

	private String comment;

	private String name;

	private String number;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:00", timezone = "GMT")
	private Timestamp date;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd,HH:00", timezone = "GMT")
	private Timestamp timeStamp;

	@com.fasterxml.jackson.annotation.JsonBackReference("arearef")
	private ReportDTO report;
	@com.fasterxml.jackson.annotation.JsonManagedReference("assetref")
	private Set<AssetDTO> assets;
	@com.fasterxml.jackson.annotation.JsonManagedReference("noteref")
	private Set<NoteDTO> notes;

	public AreaDTO() {
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

	public ReportDTO getReport() {
		return this.report;
	}

	public void setReport(ReportDTO report) {
		this.report = report;
	}

	public Set<AssetDTO> getAssets() {
		return this.assets;
	}

	public void setAssets(Set<AssetDTO> assets) {
		this.assets = assets;
	}

	public AssetDTO addAsset(AssetDTO asset) {
		getAssets().add(asset);
		asset.setArea(this);

		return asset;
	}

	public AssetDTO removeAsset(AssetDTO asset) {
		getAssets().remove(asset);
		asset.setArea(null);

		return asset;
	}

	public Set<NoteDTO> getNotes() {
		return this.notes;
	}

	public void setNotes(Set<NoteDTO> notes) {
		this.notes = notes;
	}

	public NoteDTO addNote(NoteDTO note) {
		getNotes().add(note);
		note.setArea(this);

		return note;
	}

	public NoteDTO removeNote(NoteDTO note) {
		getNotes().remove(note);
		note.setArea(null);

		return note;
	}

}