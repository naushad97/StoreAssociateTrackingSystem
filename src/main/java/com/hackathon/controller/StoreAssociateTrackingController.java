package com.hackathon.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.dto.LocationOfAssociateRsp;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.service.StoreAssociateTrackingService;

@RestController
@RequestMapping(value = "/storeAssociateTracking")
public class StoreAssociateTrackingController {

	@Autowired
	StoreAssociateTrackingService storeAssociateTrackingServiceImpl;

	@RequestMapping(method = RequestMethod.POST, value = "/saveBeaconData")
	TrackLocationByTimeRsp saveBeaconData(@RequestBody TrackLocationByTimeReq trackLocationByTimeReq) {

		System.out.println("trackLocationByTimeReq : uid = " + trackLocationByTimeReq);

		TrackLocationByTimeRsp trackLocationByTimeRsp = storeAssociateTrackingServiceImpl.trackLocationByTime(trackLocationByTimeReq);

		if (trackLocationByTimeRsp.isSuccessfull()) {
			System.out.println("Sucessfully done");
		} else {
			System.out.println("Failed");
		}

		return trackLocationByTimeRsp;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAllAssociateLocation")
	LocationOfAssociateRsp getAllLocationOfAssociate() {

		LocationOfAssociateRsp locationOfAssociateRsp = storeAssociateTrackingServiceImpl.getAllLocationOfAssociate();

		return locationOfAssociateRsp;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/getAllAssociateTrackingData")
	Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData() {

		return storeAssociateTrackingServiceImpl.getAllAssociateTrackingData();

	}
}
