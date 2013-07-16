package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonBackReference;
//import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.Date;
//import java.util.Set;


/**
 * The persistent class for the Note database table.
 * 
 */
@Entity
@NamedQuery(name="Note.findAll", query="SELECT n FROM Note n")
public class Note implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
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

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	private byte trackableActionItem;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="Area_areaId")
	private Area area;

	public Note() {
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

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public byte getTrackableActionItem() {
		return this.trackableActionItem;
	}

	public void setTrackableActionItem(byte trackableActionItem) {
		this.trackableActionItem = trackableActionItem;
	}

	

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	

}