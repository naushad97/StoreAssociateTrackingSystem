package com.hackathon.service;

import java.util.List;
import java.util.Map;

import com.hackathon.dto.*;
import com.hackathon.model.AssociateAccountDetails;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.model.BeaconDetails;
import com.hackathon.model.ZoneDetails;

public interface StoreAssociateTrackingService {

	AssociateLogin doLogin(AssociateAccountDetails associateAccountDetails);

	TrackLocationByTimeRsp trackLocationByTime(TrackLocationByTimeReq trackLocationByTimeReq);

	LocationOfAssociateRsp getAllLocationOfAssociate();

	Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData();

	List<AssociateAccountDetails> getAssociateAccounts();

	List<BeaconDetails> getBeaconDetails();

	List<ZoneDetails> getZoneDetails();

	void sendNotification(AssociateRelocationRQ associateId);

	void registerMobileRegistrationId(DeviceRegistrationReq associateReq);

}
