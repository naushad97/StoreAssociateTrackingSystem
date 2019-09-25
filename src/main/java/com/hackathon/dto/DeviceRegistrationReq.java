package com.hackathon.dto;

import java.io.Serializable;

public class DeviceRegistrationReq implements Serializable {

	private static final long serialVersionUID = 1L;
	private String associateId;

	private String registrationId;

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

}