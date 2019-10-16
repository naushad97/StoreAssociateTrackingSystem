package com.hackathon.service;

import java.util.List;
import java.util.Map;

import com.hackathon.dto.AssociateLogin;
import com.hackathon.dto.AssociateRelocationRQ;
import com.hackathon.dto.AssociateTokenRQ;
import com.hackathon.dto.DeviceRegistrationReq;
import com.hackathon.dto.LocationOfAssociateRsp;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.model.AssociateAccountDetails;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.model.BeaconAssociateLocation;
import com.hackathon.model.BeaconDetails;
import com.hackathon.model.ZoneDetails;

public interface StoreAssociateTrackingService {

	AssociateLogin doLogin(AssociateAccountDetails associateAccountDetails);
	AssociateAccountDetails userLogin(AssociateAccountDetails associateAccountDetails);

	TrackLocationByTimeRsp trackLocationByTime(TrackLocationByTimeReq trackLocationByTimeReq);

	LocationOfAssociateRsp getAllLocationOfAssociate();

	Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData();

	List<AssociateAccountDetails> getAssociateAccounts();

	List<BeaconDetails> getBeaconDetails();

	List<ZoneDetails> getZoneDetails();
	
	void loadAWSDataIntoMemory();

	void sendNotification(AssociateRelocationRQ associateId);

	void registerMobileRegistrationId(DeviceRegistrationReq associateReq);
	LocationOfAssociateRsp getAllLocationOfAssociate(String zoneId);
	void setFcmToken(AssociateTokenRQ associateTokenRQ);
	Map<BeaconAssociateLocation, Long> getAllSavedScannedData();

}
