package com.hackathon.dto;

import java.io.Serializable;

public class RelocateAssociateReq implements Serializable {

	private static final long serialVersionUID = -8474889089766632048L;
	private String associateId;
	private String destinationSection;

	public String getAssociateId() {
		return associateId;
	}

	public void setAssociateId(String associateId) {
		this.associateId = associateId;
	}

	public String getDestinationSection() {
		return destinationSection;
	}

	public void setDestinationSection(String destinationSection) {
		this.destinationSection = destinationSection;
	}
}