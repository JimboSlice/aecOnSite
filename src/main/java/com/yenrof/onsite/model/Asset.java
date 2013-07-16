package com.yenrof.onsite.model;

import java.io.Serializable;

import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonBackReference;
//import org.codehaus.jackson.annotate.JsonManagedReference;

import java.util.Date;
//import java.util.Set;


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
	private long assetId;

	private byte appraisalFlag;

	private String description;

	private double initvalue;

	private String name;

	@Temporal(TemporalType.DATE)
	private Date purchaseDate;

	@Temporal(TemporalType.DATE)
	private Date timeStamp;

	private String type;

	//bi-directional many-to-one association to Area
	@ManyToOne
	@JsonBackReference
	@JoinColumn(name="Area_areaId")
	private Area area;

	public Asset() {
	}

	public long getAssetId() {
		return this.assetId;
	}

	public void setAssetId(long assetId) {
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

	public Area getArea() {
		return this.area;
	}

	public void setArea(Area area) {
		this.area = area;
	}


	

}