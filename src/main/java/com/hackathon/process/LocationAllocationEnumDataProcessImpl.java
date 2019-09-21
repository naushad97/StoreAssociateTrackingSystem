package com.hackathon.process;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.hackathon.dto.LocationAndAssociateDetails;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.enums.AssociateDetailsEnum;
import com.hackathon.enums.BeaconDetailsEnum;
import com.hackathon.enums.ZoneDetailsEnum;
import com.hackathon.mapping.BeaconAssociateLocationMapping;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.model.AssociateSectionLocation;
import com.hackathon.model.BeaconAssociateLocation;

@Component
public class LocationAllocationEnumDataProcessImpl implements LocationAllocationMapProcess {

	private Map<BeaconAssociateLocation, Long> associateLocationMap = new WeakHashMap<BeaconAssociateLocation, Long>();
	private Map<AssociateSectionLocation, List<AssociateInSectionTimeRange>> associateTrackermap = new ConcurrentHashMap<AssociateSectionLocation, List<AssociateInSectionTimeRange>>();

	public boolean setReqDataToMap(TrackLocationByTimeRsp trackLocationByTimeRsp,
			TrackLocationByTimeReq trackLocationByTimeReq) {

		String uid = trackLocationByTimeReq.getProximityUUID();
		String appSID = trackLocationByTimeReq.getUserId();
		long actionTime = trackLocationByTimeReq.getScanTimeInMillis();

		if (!validateUidAndappSID(trackLocationByTimeRsp, uid, appSID)) {
			return false;
		}

		BeaconDetailsEnum beaconDetail = BeaconDetailsEnum.findBeaconIDByUID(uid);
		int beaconId = beaconDetail.getBeaconId();
		ZoneDetailsEnum zone = ZoneDetailsEnum.findZoneByBiconId(beaconDetail.getUid());
		int associateId = AssociateDetailsEnum.findAssociateByASID(appSID).getAssociateId();

		BeaconAssociateLocation beaconAssociateLocation = BeaconAssociateLocationMapping.mapToModel(beaconId,
				associateId, zone, trackLocationByTimeReq);
		setReqDataToTracker(associateId, beaconId, actionTime);
		associateLocationMap.put(beaconAssociateLocation, actionTime);

		System.out.println(associateLocationMap);
		System.out.println(associateTrackermap);

		return true;

	}

	@Override
	public boolean validateUidAndappSID(TrackLocationByTimeRsp trackLocationByTimeRsp, String uid, String appSID) {

		if (!BeaconDetailsEnum.checkByUID(uid)) {
			populateErrorMsg(trackLocationByTimeRsp, "UID = " + uid + ", provided in request, does not matched");
			return false;
		}

		if (!ZoneDetailsEnum.checkByBiconId(uid)) {
			populateErrorMsg(trackLocationByTimeRsp, "Zone not found by uid = " + uid);
			return false;
		}

		if (!AssociateDetailsEnum.checkByASID(appSID)) {
			populateErrorMsg(trackLocationByTimeRsp, "Apps ID = " + appSID + ", provided in request, does not matched");
			return false;
		}

		return true;
	}

	private void populateErrorMsg(TrackLocationByTimeRsp trackLocationByTimeRsp, String msg) {
		trackLocationByTimeRsp.setSuccessfull(false);
		trackLocationByTimeRsp.setMessage(msg);
		trackLocationByTimeRsp.setStatus(404);
	}

	public List<LocationAndAssociateDetails> getAllLocationOfAssociate() {
		List<LocationAndAssociateDetails> locationAndAssociateDetailsList = new ArrayList<LocationAndAssociateDetails>();
		associateLocationMap.forEach((k, v) -> {
			if(((System.currentTimeMillis() - v)/1000) < 30) {
				LocationAndAssociateDetails locationAndAssociateDetails = new LocationAndAssociateDetails();
				locationAndAssociateDetails.setTime(v);
				processBeaconZoneAndAssociate(locationAndAssociateDetails, k);
				locationAndAssociateDetailsList.add(locationAndAssociateDetails);
			}
		});

		return locationAndAssociateDetailsList;
	}
	
	public Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData() {
		final Map<String, List<AssociateInSectionTimeRange>> associateTracker = new HashMap<>();
		associateTrackermap.forEach((k, v) -> {
			associateTracker.put(String.valueOf(k.getAssociateId()), associateTrackermap.get(k));
		});

		return associateTracker;
	}

	private void processBeaconZoneAndAssociate(LocationAndAssociateDetails locationAndAssociateDetails,
			BeaconAssociateLocation beaconAssociateLocation) {

		if (beaconAssociateLocation != null && locationAndAssociateDetails != null) {
			BeaconDetailsEnum beacon = BeaconDetailsEnum.findBeaconByBeaconId(beaconAssociateLocation.getBeaconId());
			AssociateDetailsEnum associate = AssociateDetailsEnum
					.findAssociateByAssociateId(beaconAssociateLocation.getAssociateId());
			if (associate != null) {
				locationAndAssociateDetails.setAssociateAsid(associate.getAsid());
				locationAndAssociateDetails.setAssociateName(associate.getName());
			}

			if (beacon != null) {
				locationAndAssociateDetails.setBiconName(beacon.getName());
			}
			if (beaconAssociateLocation.getZone() != null) {
				locationAndAssociateDetails.setZoneName(beaconAssociateLocation.getZone().getZoneName());
				locationAndAssociateDetails.setSectionName(beaconAssociateLocation.getZone().getSection());
			}

			locationAndAssociateDetails.setDistance(beaconAssociateLocation.getDistance());
			locationAndAssociateDetails.setDistanceUnit(beaconAssociateLocation.getDistanceUnit());
			locationAndAssociateDetails.setHandshakeTimeInNano(beaconAssociateLocation.getHandshakeTimeInNano());
			locationAndAssociateDetails.setMajor(beaconAssociateLocation.getMajor());
			locationAndAssociateDetails.setMinor(beaconAssociateLocation.getMinor());
			locationAndAssociateDetails.setPower(beaconAssociateLocation.getPower());
			locationAndAssociateDetails.setProximity(beaconAssociateLocation.getProximity());
			locationAndAssociateDetails.setProximityUUID(beaconAssociateLocation.getProximityUUID());
			locationAndAssociateDetails.setRssi(beaconAssociateLocation.getRssi());
			locationAndAssociateDetails.setScanDateTimeStamp(beaconAssociateLocation.getScanDateTimeStamp());
			locationAndAssociateDetails.setScanTimeInMillis(beaconAssociateLocation.getScanTimeInMillis());
			locationAndAssociateDetails.setType(beaconAssociateLocation.getType());
			locationAndAssociateDetails.setUserId(beaconAssociateLocation.getUserId());
		}

	}

	public boolean setReqDataToTracker(final int associateId, final int sectionId, final long scanTime) {
		final Map<AssociateSectionLocation, List<AssociateInSectionTimeRange>> trackerMap = associateTrackermap;
		final AssociateSectionLocation requestAssociateSectionLocation = new AssociateSectionLocation();
		requestAssociateSectionLocation.setAssociateId(associateId);
		requestAssociateSectionLocation.setSectionId(sectionId);
		final Set<AssociateSectionLocation> keySet = associateTrackermap.keySet();
		if (!keySet.contains(requestAssociateSectionLocation)) {
			final AssociateInSectionTimeRange associateInSectionTimeRange = new AssociateInSectionTimeRange();
			associateInSectionTimeRange.setEntryTime(scanTime);
			associateInSectionTimeRange.setExitTime(scanTime);
			associateInSectionTimeRange.setSection(String.valueOf(requestAssociateSectionLocation.getSectionId()));
			associateInSectionTimeRange.setZone(ZoneDetailsEnum.findZoneBySectionId(String.valueOf(requestAssociateSectionLocation.getSectionId())).getZoneName());
			final List<AssociateInSectionTimeRange> trackerList = new ArrayList<>();
			trackerList.add(associateInSectionTimeRange);
			trackerMap.put(requestAssociateSectionLocation, trackerList);
		} else {
			final List<AssociateInSectionTimeRange> associateInSectionList = associateTrackermap
					.get(requestAssociateSectionLocation);
			final Optional<AssociateInSectionTimeRange> findFirst = associateInSectionList.stream()
					.filter(asl -> getTimeDiffInSeconds(asl.getExitTime(), scanTime) < 30).findFirst();
			if (findFirst.isPresent()) {
				findFirst.get().setExitTime(scanTime);
			} else {
				final AssociateInSectionTimeRange associateInSectionTimeRange = new AssociateInSectionTimeRange();
				associateInSectionTimeRange.setEntryTime(scanTime);
				associateInSectionTimeRange.setExitTime(scanTime);
				associateInSectionTimeRange.setSection(String.valueOf(requestAssociateSectionLocation.getSectionId()));
				associateInSectionTimeRange.setZone(ZoneDetailsEnum.findZoneBySectionId(String.valueOf(requestAssociateSectionLocation.getSectionId())).getZoneName());
				final List<AssociateInSectionTimeRange> trackerList = new ArrayList<>();
				trackerList.add(associateInSectionTimeRange);
				trackerMap.get(requestAssociateSectionLocation).add(associateInSectionTimeRange);
			}

		}
		return false;
	}

	private long getTimeDiffInSeconds(final long exitTime, final long requestTime) {
		long timeDiff = 0;
		timeDiff = (requestTime - exitTime) / 1000;
		return timeDiff;
	}

}
