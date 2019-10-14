package com.hackathon.controller;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hackathon.dto.AssociateLogin;
import com.hackathon.dto.AssociateRelocationRQ;
import com.hackathon.dto.DeviceRegistrationReq;
import com.hackathon.dto.LocationOfAssociateRsp;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.model.AssociateAccountDetails;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.model.BeaconDetails;
import com.hackathon.model.ZoneDetails;
import com.hackathon.service.StoreAssociateTrackingService;

@RestController
@CrossOrigin
@RequestMapping(value = "/storeAssociateTracking")
public class StoreAssociateTrackingController {

	private static Logger logger = Logger.getLogger(StoreAssociateTrackingController.class.getName());

	@Autowired
	private StoreAssociateTrackingService storeAssociateTrackingServiceImpl;

	@RequestMapping(method = RequestMethod.POST, value = "/doLogin")
	AssociateLogin doLogin(@RequestBody AssociateAccountDetails associateAccount) {

		logger.info("ReqData=" + associateAccount.toString());
		loadAWSDataIntoMemory();

		return storeAssociateTrackingServiceImpl.doLogin(associateAccount);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/saveBeaconData")
	TrackLocationByTimeRsp saveBeaconData(@RequestBody TrackLocationByTimeReq trackLocationByTimeReq) {

		loadAWSDataIntoMemory();
		System.out.println("trackLocationByTimeReq : uid = " + trackLocationByTimeReq);

		TrackLocationByTimeRsp trackLocationByTimeRsp = storeAssociateTrackingServiceImpl
				.trackLocationByTime(trackLocationByTimeReq);

		if (trackLocationByTimeRsp.isSuccessfull()) {
			System.out.println("Sucessfully done");
		} else {
			System.out.println("Failed");
		}

		return trackLocationByTimeRsp;
	}
	
	

	/**
	 * TO get real-time data in all zone in 30 last sec
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAllAssociateLocation")
	LocationOfAssociateRsp getAllLocationOfAssociate() {
 
		loadAWSDataIntoMemory();

		LocationOfAssociateRsp locationOfAssociateRsp = storeAssociateTrackingServiceImpl.getAllLocationOfAssociate();

		return locationOfAssociateRsp;
	}
	
	/**
	 * TO get real-time data in a particular zone in 30 last sec
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAllAssociateLocation/{zoneId}")
	LocationOfAssociateRsp getAllLocationOfAssociate(@PathVariable String zoneId) {
 
		loadAWSDataIntoMemory();

		LocationOfAssociateRsp locationOfAssociateRsp = storeAssociateTrackingServiceImpl.getAllLocationOfAssociate(zoneId);

		return locationOfAssociateRsp;
	}

	/**
	 * Get analytics data in through out day
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/getAllAssociateTrackingData")
	Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData() {

		loadAWSDataIntoMemory();

		return storeAssociateTrackingServiceImpl.getAllAssociateTrackingData();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getAssociateDetails")
	List<AssociateAccountDetails> getAssociateAccounts() {
		return storeAssociateTrackingServiceImpl.getAssociateAccounts();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getBeaconDetails")
	List<BeaconDetails> getBeaconDetails() {
		return storeAssociateTrackingServiceImpl.getBeaconDetails();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/getZoneDetails")
	List<ZoneDetails> getZoneDetails() {
		return storeAssociateTrackingServiceImpl.getZoneDetails();
	}

	@PostMapping("/notification")
	public void sendNotification(@RequestBody final AssociateRelocationRQ associateRelocationRQ) {
		storeAssociateTrackingServiceImpl.sendNotification(associateRelocationRQ);
	}

	@PostMapping("/register")
	public void registerMobileRegistrationId(@RequestBody final DeviceRegistrationReq deviceRegistrationReq) {
		storeAssociateTrackingServiceImpl.registerMobileRegistrationId(deviceRegistrationReq);
	}

	private void loadAWSDataIntoMemory() {
		getAssociateAccounts();
		getBeaconDetails();
		getZoneDetails();
	}

}
