package com.yenrof.onsite.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the Asset database table.
 * 
 */
@Entity
@NamedQuery(name="Asset.findAll", query="SELECT a FROM Asset a")
public class Asset implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private String assetId;

	private byte appraisalFlag;

	private String description;

	private double initvalue;

	private String name;

	@Temporal(TemporalType.DATE)
	private Date purchaseDate;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	private String type;

	private byte[] voiceData;

	//bi-directional many-to-one association to Area
	@ManyToOne(cascade={CascadeType.ALL})
	@JoinColumn(name="Area_areaId")
	private Area area;

	//bi-directional many-to-one association to Picture
	@OneToMany(mappedBy="asset")
	private List<Picture> pictures;

	public Asset() {
	}

	public String getAssetId() {
		return this.assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public byte getAppraisalFlag() {
		return this.appraisalFlag;
	}

	public void setAppraisalFlag(byte appraisalFlag) {
		this.appraisalFlag = appraisalFlag;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getInitvalue() {
		return this.initvalue;
	}

	public void setInitvalue(double initvalue) {
		this.initvalue = initvalue;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getPurchaseDate() {
		return this.purchaseDate;
	}

	public void setPurchaseDate(Date purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public Date getTimeStamp() {
		return this.timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public byte[] getVoiceData() {
		return this.voiceData;
	}

	public void setVoiceData(byte[] voiceData) {
		this.voiceData = voiceData;
	}

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public List<Picture> getPictures() {
		return this.pictures;
	}

	public void setPictures(List<Picture> pictures) {
		this.pictures = pictures;
	}

	public Picture addPicture(Picture picture) {
		getPictures().add(picture);
		picture.setAsset(this);

		return picture;
	}

	public Picture removePicture(Picture picture) {
		getPictures().remove(picture);
		picture.setAsset(null);

		return picture;
	}

}