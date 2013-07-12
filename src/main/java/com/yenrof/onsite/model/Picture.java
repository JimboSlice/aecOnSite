package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonBackReference;

import java.util.Date;


/**
 * The persistent class for the Picture database table.
 * 
 */
@Entity
@NamedQuery(name="Picture.findAll", query="SELECT p FROM Picture p")
public class Picture implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String pictureId;

	private String comment;

	private double latitude;

	private double longitude;

	private byte[] thePic;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	//bi-directional many-to-one association to Asset
	@ManyToOne
	//@JsonBackReference
	@JoinColumn(name="Asset_assetId")
	private Asset asset;

	//bi-directional many-to-one association to Note
	@ManyToOne
	//@JsonBackReference
	@JoinColumn(name="Note_noteId")
	private Note note;

	public Picture() {
	}

	public String getPictureId() {
		return this.pictureId;
	}

	public void setPictureId(String pictureId) {
		this.pictureId = pictureId;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public Asset getAsset() {
		return this.asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Note getNote() {
		return this.note;
	}

	public void setNote(Note note) {
		this.note = note;
	}

}