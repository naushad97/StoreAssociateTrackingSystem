package com.hackathon.dto;

import java.io.Serializable;
import java.util.List;

public class AssociateRelocationRQ implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<RelocateAssociateReq> relocateAssociateReqs;

	public List<RelocateAssociateReq> getRelocateAssociateReqs() {
		return relocateAssociateReqs;
	}

	public void setRelocateAssociateReqs(List<RelocateAssociateReq> relocateAssociateReqs) {
		this.relocateAssociateReqs = relocateAssociateReqs;
	}

}
