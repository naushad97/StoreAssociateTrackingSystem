package com.hackathon.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LocationOfAssociateRsp extends BaseRsp implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<LocationAndAssociateDetails> locationAndAssociateDetailsList;

	public List<LocationAndAssociateDetails> getLocationAndAssociateDetailsList() {
		if (locationAndAssociateDetailsList == null) {
			locationAndAssociateDetailsList = new ArrayList<LocationAndAssociateDetails>();
		}
		return locationAndAssociateDetailsList;
	}

}
