package com.yenrof.onsite.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;


/**
 * The DTO class for the Note database table.
 * 
 */
public class NoteDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private long noteId;

	private String displayPic;

	private boolean itemResolved;

	private double latitude;

	private double longitude;

	private String note;

	private int picCounter;

	private int picOrientation;

	private float picScale;

	private byte[] thePic;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "GMT")
	private Timestamp timeStamp;

	private byte trackableActionItem;
	
	@com.fasterxml.jackson.annotation.JsonBackReference("noteref")
	private AreaDTO area;

	public NoteDTO() {
	}

	public long getNoteId() {
		return this.noteId;
	}

	public void setNoteId(long noteId) {
		this.noteId = noteId;
	}

	public String getDisplayPic() {
		return this.displayPic;
	}

	public void setDisplayPic(String displayPic) {
		this.displayPic = displayPic;
	}

	public boolean getItemResolved() {
		return this.itemResolved;
	}

	public void setItemResolved(boolean itemResolved) {
		this.itemResolved = itemResolved;
	}

	public double getLatitude() {
		return this.latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return this.longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public String getNote() {
		return this.note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public int getPicCounter() {
		return this.picCounter;
	}

	public void setPicCounter(int picCounter) {
		this.picCounter = picCounter;
	}

	public int getPicOrientation() {
		return this.picOrientation;
	}

	public void setPicOrientation(int picOrientation) {
		this.picOrientation = picOrientation;
	}

	public float getPicScale() {
		return this.picScale;
	}

	public void setPicScale(float picScale) {
		this.picScale = picScale;
	}

	public byte[] getThePic() {
		return this.thePic;
	}

	public void setThePic(byte[] thePic) {
		this.thePic = thePic;
	}

	public Timestamp getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Timestamp timeStamp) {
		this.timeStamp = timeStamp;
	}

	public byte getTrackableActionItem() {
		return this.trackableActionItem;
	}

	public void setTrackableActionItem(byte trackableActionItem) {
		this.trackableActionItem = trackableActionItem;
	}

	

	public AreaDTO getArea() {
		return this.area;
	}

	public void setArea(AreaDTO area) {
		this.area = area;
	}

	

}