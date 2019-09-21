package com.hackathon.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackathon.config.AppProperties;
import com.hackathon.dto.LocationAndAssociateDetails;
import com.hackathon.dto.LocationOfAssociateRsp;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.process.LocationAllocationEnumDataProcessImpl;
import com.hackathon.validation.StoreAssociateTrackingValidation;

@Component
public class StoreAssociateTrackingServiceImpl implements StoreAssociateTrackingService {

	@Autowired
	private StoreAssociateTrackingValidation storeAssociateTrackingValidation;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private LocationAllocationEnumDataProcessImpl locationAllocationEnumDataProcessImpl;

	@Override
	public TrackLocationByTimeRsp trackLocationByTime(TrackLocationByTimeReq trackLocationByTimeReq) {

		TrackLocationByTimeRsp trackLocationByTimeRsp = new TrackLocationByTimeRsp();

		if (!storeAssociateTrackingValidation.validateInputReq(trackLocationByTimeReq, trackLocationByTimeRsp)) {
			return trackLocationByTimeRsp;
		} else {
			if (appProperties.getDatabaseCall()) {
				System.out.println("From Database");
			} else {
				trackLocationByTimeRsp.setSuccessfull(locationAllocationEnumDataProcessImpl
						.setReqDataToMap(trackLocationByTimeRsp, trackLocationByTimeReq));
			}
		}
		return trackLocationByTimeRsp;
	}

	@Override
	public LocationOfAssociateRsp getAllLocationOfAssociate() {
		LocationOfAssociateRsp locationOfAssociateRsp = new LocationOfAssociateRsp();

		List<LocationAndAssociateDetails> locationAndAssociateDetailsList = locationAllocationEnumDataProcessImpl
				.getAllLocationOfAssociate();

		locationOfAssociateRsp.getLocationAndAssociateDetailsList().addAll(locationAndAssociateDetailsList);

		return locationOfAssociateRsp;
	}

	@Override
	public Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData() {
		
		return locationAllocationEnumDataProcessImpl.getAllAssociateTrackingData();
	}

}
