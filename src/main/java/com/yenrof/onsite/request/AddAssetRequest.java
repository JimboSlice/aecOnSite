package com.yenrof.onsite.request;

import java.io.Serializable;

import com.yenrof.onsite.dto.AssetDTO;

/**
 * The DTO request class for the Asset database table.
 * 
 */
public class AddAssetRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long personId;
	private long areaId;
	private AssetDTO asset = new AssetDTO();
	public AssetDTO getAssetDTO() {
		return asset;
	}
	public void setAsset(AssetDTO asset) {
		this.asset = asset;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	}
