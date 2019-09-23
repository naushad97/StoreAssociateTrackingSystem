package com.hackathon.mapping;

import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.model.BeaconAssociateLocation;
import com.hackathon.model.ZoneDetails;

public class BeaconAssociateLocationMapping {

	public static BeaconAssociateLocation mapToModel(int beaconId, int associateId, ZoneDetails zone,
			TrackLocationByTimeReq trackLocationByTimeReq) {
		BeaconAssociateLocation beaconAssociateLocation = new BeaconAssociateLocation();
		beaconAssociateLocation.setBeaconId(beaconId);
		beaconAssociateLocation.setAssociateId(associateId);
		beaconAssociateLocation.setZone(zone);
		beaconAssociateLocation.setDistance(trackLocationByTimeReq.getDistance());
		beaconAssociateLocation.setDistanceUnit(trackLocationByTimeReq.getDistanceUnit());
		beaconAssociateLocation.setHandshakeTimeInNano(trackLocationByTimeReq.getHandshakeTimeInNano());
		beaconAssociateLocation.setMajor(trackLocationByTimeReq.getMajor());
		beaconAssociateLocation.setMinor(trackLocationByTimeReq.getMinor());
		beaconAssociateLocation.setPower(trackLocationByTimeReq.getPower());
		beaconAssociateLocation.setProximity(trackLocationByTimeReq.getProximity());
		beaconAssociateLocation.setProximityUUID(trackLocationByTimeReq.getProximityUUID());
		beaconAssociateLocation.setRssi(trackLocationByTimeReq.getRssi());
		beaconAssociateLocation.setScanDateTimeStamp(trackLocationByTimeReq.getScanDateTimeStamp());
		beaconAssociateLocation.setScanTimeInMillis(trackLocationByTimeReq.getScanTimeInMillis());
		beaconAssociateLocation.setType(trackLocationByTimeReq.getType());
		beaconAssociateLocation.setUserId(trackLocationByTimeReq.getUserId());

		return beaconAssociateLocation;
	}

}
