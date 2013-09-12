package com.yenrof.onsite.dto;

import java.io.Serializable;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * The DTO class for the Picture database table.
 * 
 */
public class PictureDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long pictureId;

	private String comment;

	private double latitude;

	private double longitude;

	private byte[] thePic;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "GMT")
	private Date timeStamp;

	@com.fasterxml.jackson.annotation.JsonBackReference("assetref")
	private AssetDTO asset;

	@com.fasterxml.jackson.annotation.JsonBackReference("noteref")
	private NoteDTO note;

	public PictureDTO() {
	}

	public long getPictureId() {
		return this.pictureId;
	}

	public void setPictureId(long pictureId) {
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

	public AssetDTO getAsset() {
		return this.asset;
	}

	public void setAsset(AssetDTO asset) {
		this.asset = asset;
	}

	public NoteDTO getNote() {
		return this.note;
	}

	public void setNote(NoteDTO note) {
		this.note = note;
	}

}