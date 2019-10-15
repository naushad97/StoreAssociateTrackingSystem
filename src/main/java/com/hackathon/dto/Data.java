package com.hackathon.dto;

import java.io.Serializable;

public class Data implements Serializable {

	private static final long serialVersionUID = 1L;
	private String associateId;
	private String toZoneId;
	private String toZoneName;
	private String authorization;

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getToZoneId() {
		return toZoneId;
	}

	public void setToZoneId(String toZoneId) {
		this.toZoneId = toZoneId;
	}

	public String getToZoneName() {
		return toZoneName;
	}

	public void setToZoneName(String toZoneName) {
		this.toZoneName = toZoneName;
	}

	public String getAuthorization() {
		return authorization;
	}

	public void setAuthorization(String authorization) {
		this.authorization = authorization;
	}

	@Override
	public String toString() {
		return "Data [associateId=" + associateId + ", toZoneId=" + toZoneId + ", toZoneName=" + toZoneName
				+ ", authorization=" + authorization + "]";
	}
	
	

}
