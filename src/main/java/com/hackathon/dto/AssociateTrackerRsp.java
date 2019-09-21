package com.hackathon.dto;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.hackathon.model.AssociateInSectionTimeRange;

public class AssociateTrackerRsp extends BaseRsp implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, List<AssociateInSectionTimeRange>> associateTracker;
	public Map<String, List<AssociateInSectionTimeRange>> getAssociateTracker() {
		return associateTracker;
	}
	public void setAssociateTracker(Map<String, List<AssociateInSectionTimeRange>> associateTracker) {
		this.associateTracker = associateTracker;
	}

	
}
