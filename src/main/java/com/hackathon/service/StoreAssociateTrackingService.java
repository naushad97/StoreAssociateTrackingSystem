package com.hackathon.service;

import java.util.List;
import java.util.Map;

import com.hackathon.dto.LocationOfAssociateRsp;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.model.AssociateInSectionTimeRange;

public interface StoreAssociateTrackingService {

	TrackLocationByTimeRsp trackLocationByTime(TrackLocationByTimeReq trackLocationByTimeReq);

	LocationOfAssociateRsp getAllLocationOfAssociate();
	
	Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData();

}
