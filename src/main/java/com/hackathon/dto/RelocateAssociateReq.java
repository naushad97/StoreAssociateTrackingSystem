package com.hackathon.dto;

import java.io.Serializable;

public class RelocateAssociateReq implements Serializable {

	private static final long serialVersionUID = -8474889089766632048L;
	
	private String associateId;
	private String toZoneId;
	private String toFcmToken;
	
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
	public String getToFcmToken() {
		return toFcmToken;
	}
	public void setToFcmToken(String toFcmToken) {
		this.toFcmToken = toFcmToken;
	}
	
}