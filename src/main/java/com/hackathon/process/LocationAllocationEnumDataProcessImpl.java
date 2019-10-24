package com.hackathon.process;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hackathon.config.AppProperties;
import com.hackathon.dto.LocationAndAssociateDetails;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.enums.AssociateDetailsEnum;
import com.hackathon.mapping.BeaconAssociateLocationMapping;
import com.hackathon.model.AssociateAccountDetails;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.model.AssociateSectionLocation;
import com.hackathon.model.BeaconAssociateLocation;
import com.hackathon.model.BeaconDetails;
import com.hackathon.model.InMemoryData;
import com.hackathon.model.ZoneDetails;

@Component
public class LocationAllocationEnumDataProcessImpl implements LocationAllocationMapProcess {

	@Autowired
	AppProperties appProperties;

	private Map<BeaconAssociateLocation, Long> associateLocationMap = new WeakHashMap<BeaconAssociateLocation, Long>();
	private Map<AssociateSectionLocation, List<AssociateInSectionTimeRange>> associateTrackermap = new ConcurrentHashMap<AssociateSectionLocation, List<AssociateInSectionTimeRange>>();

	public boolean setReqDataToMap(TrackLocationByTimeRsp trackLocationByTimeRsp,
			TrackLocationByTimeReq trackLocationByTimeReq) {

		String uid = trackLocationByTimeReq.getProximityUUID();
		String appSId = trackLocationByTimeReq.getUserId();
		long actionTime = trackLocationByTimeReq.getScanTimeInMillis();

		if (!validateUidAndappSID(trackLocationByTimeRsp, uid, appSId)) {
			return false;
		}

		BeaconDetails beaconDetail = InMemoryData.findBeaconDtlsByUuID(uid);
		int beaconId = beaconDetail.getBeaconId();
		ZoneDetails zone = InMemoryData.findZoneByBeaconUuId(beaconDetail.getUuid());
		AssociateAccountDetails associateAccountDetails = InMemoryData.findAssociateByAppSID(appSId);
		int associateId = associateAccountDetails.getAssociateId();

		BeaconAssociateLocation beaconAssociateLocation = BeaconAssociateLocationMapping.mapToModel(beaconId,
				associateId, zone, trackLocationByTimeReq);
		setReqDataToTracker(associateAccountDetails, beaconDetail, zone, actionTime,
				trackLocationByTimeReq.getScanDateTimeStamp());
		associateLocationMap.put(beaconAssociateLocation, actionTime);

		System.out.println(associateLocationMap);
		System.out.println(associateTrackermap);

		return true;

	}

	@Override
	public boolean validateUidAndappSID(TrackLocationByTimeRsp trackLocationByTimeRsp, String uid, String appSID) {

		if (!InMemoryData.isValidBeaconUuId(uid)) {
			populateErrorMsg(trackLocationByTimeRsp, "UID = " + uid + ", provided in request, does not matched");
			return false;
		}

		if (!InMemoryData.isZoneExistsByBeaconUuiD(uid)) {
			populateErrorMsg(trackLocationByTimeRsp, "Zone not found by uid = " + uid);
			return false;
		}

		if (!InMemoryData.isValidUser(appSID)) {
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
			if (((System.currentTimeMillis() - v) / 1000) < appProperties.getInterval()) {
				LocationAndAssociateDetails locationAndAssociateDetails = new LocationAndAssociateDetails();
				locationAndAssociateDetails.setTime(v);
				processBeaconZoneAndAssociate(locationAndAssociateDetails, k);
				locationAndAssociateDetailsList.add(locationAndAssociateDetails);
			}
		});

		return locationAndAssociateDetailsList;
	}
	
	public List<LocationAndAssociateDetails> getAllLocationOfAssociate(String zoneId) {
		List<LocationAndAssociateDetails> locationAndAssociateDetailsList = new ArrayList<LocationAndAssociateDetails>();
		associateLocationMap.forEach((k, v) -> {
			if (((System.currentTimeMillis() - v) / 1000) < appProperties.getInterval() && k.getZone().getZoneId() == Integer.parseInt(zoneId) ) {
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
			AssociateDetailsEnum associateDetailsEnum = AssociateDetailsEnum
					.findAssociateByAssociateId(k.getAssociateId());
			/*
			 * String keyVal = associateDetailsEnum.getAssociateId() + ":" +
			 * associateDetailsEnum.getName() + ":" + associateDetailsEnum.getAsid();
			 */
			String keyVal = String.valueOf(associateDetailsEnum.getAssociateId());
			associateTracker.put(keyVal, associateTrackermap.get(k));
		});

		return associateTracker;
	}

	private void processBeaconZoneAndAssociate(LocationAndAssociateDetails locationAndAssociateDetails,
			BeaconAssociateLocation beaconAssociateLocation) {

		if (beaconAssociateLocation != null && locationAndAssociateDetails != null) {
			BeaconDetails beacon = InMemoryData.findBeaconByBeaconId(beaconAssociateLocation.getBeaconId());
			AssociateAccountDetails associate = InMemoryData
					.findAssociateByAssociateId(beaconAssociateLocation.getAssociateId());
			if (associate != null) {
				locationAndAssociateDetails.setAssociateAsid(associate.getAppSId());
				locationAndAssociateDetails.setAssociateName(associate.getName());
			}

			if (beacon != null) {
				locationAndAssociateDetails.setBiconName(beacon.getName());
			}
			if (beaconAssociateLocation.getZone() != null) {
				locationAndAssociateDetails.setZoneName(beaconAssociateLocation.getZone().getZoneName());
				locationAndAssociateDetails.setSectionName(beaconAssociateLocation.getZone().getSection());
				locationAndAssociateDetails.setZoneId(String.valueOf(beaconAssociateLocation.getZone().getZoneId()));
				locationAndAssociateDetails.setCrowdCount(beaconAssociateLocation.getZone().getCrowdCount());
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

	public boolean setReqDataToTracker(final AssociateAccountDetails associateAccountDetails,
			BeaconDetails beaconDetail, final ZoneDetails zone, final long scanTime, String dateTime) {
		final Map<AssociateSectionLocation, List<AssociateInSectionTimeRange>> trackerMap = associateTrackermap;
		final AssociateSectionLocation requestAssociateSectionLocation = new AssociateSectionLocation();
		requestAssociateSectionLocation.setAssociateId(associateAccountDetails.getAssociateId());
		requestAssociateSectionLocation.setBeaconId(beaconDetail.getBeaconId());
		final Set<AssociateSectionLocation> keySet = associateTrackermap.keySet();
		if (!keySet.contains(requestAssociateSectionLocation)) {
			final AssociateInSectionTimeRange associateInSectionTimeRange = new AssociateInSectionTimeRange();
			associateInSectionTimeRange.setEntryTime(convertMiliSecToHHMMSS(scanTime));
			associateInSectionTimeRange.setExitTime(convertMiliSecToHHMMSS(scanTime));
			associateInSectionTimeRange.setTimeSpent("00:00:00");
			associateInSectionTimeRange.setSection(zone.getSection());
			associateInSectionTimeRange.setZone(zone.getZoneName());
			associateInSectionTimeRange.setBiconID(beaconDetail.getUuid());
			associateInSectionTimeRange.setBiconName(beaconDetail.getName());
			associateInSectionTimeRange.setDateOfAction(dateTime);
			associateInSectionTimeRange.setEmpName(associateAccountDetails.getName());
			associateInSectionTimeRange.setEmpId(String.valueOf(associateAccountDetails.getAssociateId()));
			associateInSectionTimeRange.setUserId(String.valueOf(associateAccountDetails.getUserId()));
			final List<AssociateInSectionTimeRange> trackerList = new ArrayList<>();
			trackerList.add(associateInSectionTimeRange);
			trackerMap.put(requestAssociateSectionLocation, trackerList);
		} else {
			final List<AssociateInSectionTimeRange> associateInSectionList = associateTrackermap
					.get(requestAssociateSectionLocation);
			final Optional<AssociateInSectionTimeRange> findFirst = associateInSectionList.stream()
					.filter(asl -> getTimeDiffInSeconds(convertHHMMYYToMiliSec(asl.getExitTime()),
							convertHHMMYYToMiliSec(convertMiliSecToHHMMSS(scanTime))) < appProperties.getInterval())
					.findFirst();
			if (findFirst.isPresent()) {
				AssociateInSectionTimeRange associateInSectionTimeRange = findFirst.get();
				associateInSectionTimeRange.setExitTime(convertMiliSecToHHMMSS(scanTime));
				associateInSectionTimeRange.setTimeSpent(
						convertSecToHHMMYY(convertHHMMYYToMiliSec(associateInSectionTimeRange.getExitTime())
								- convertHHMMYYToMiliSec(associateInSectionTimeRange.getEntryTime())));
				associateInSectionTimeRange.setDateOfAction(dateTime);
			} else {
				final AssociateInSectionTimeRange associateInSectionTimeRange = new AssociateInSectionTimeRange();
				associateInSectionTimeRange.setEntryTime(convertMiliSecToHHMMSS(scanTime));
				associateInSectionTimeRange.setExitTime(convertMiliSecToHHMMSS(scanTime));
				associateInSectionTimeRange.setTimeSpent("00:00:00");
				associateInSectionTimeRange.setSection(zone.getSection());
				associateInSectionTimeRange.setZone(zone.getZoneName());
				associateInSectionTimeRange.setBiconID(beaconDetail.getUuid());
				associateInSectionTimeRange.setBiconName(beaconDetail.getName());
				associateInSectionTimeRange.setDateOfAction(dateTime);
				associateInSectionTimeRange.setEmpName(associateAccountDetails.getName());
				associateInSectionTimeRange.setEmpId(String.valueOf(associateAccountDetails.getAssociateId()));
				associateInSectionTimeRange.setUserId(String.valueOf(associateAccountDetails.getUserId()));
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

	private String convertMiliSecToHHMMSS(final long millis) {
		return (new SimpleDateFormat("hh:mm:ss")).format(new Date(millis));
	}

	private String convertSecToHHMMYY(final long millis) {
		String sec = "00";
		String min = "00";
		String hr = "00";
		int sec1 = (int) (millis / 1000);
		int sec2 = sec1 % 60;
		if (sec2 < 10 && sec2 > 0) {
			sec = "0" + sec2;
		} else if (sec2 >= 10 && sec2 < 60) {
			sec = "" + sec2;
		}

		int sec3 = sec1 - sec2;
		int min1 = sec3 / 60;
		int min2 = min1 % 60;

		if (min2 < 10 && min2 > 0) {
			min = "0" + min2;
		} else if (min2 >= 10 && min2 < 60) {
			min = "" + min2;
		}

		int hr1 = min2 / 60;

		if (hr1 < 10 && hr1 > 0) {
			hr = "0" + hr1;
		} else {
			hr = "" + hr1;
		}

		return hr + ":" + min + ":" + sec;
	}

	private long convertHHMMYYToMiliSec(final String time) {
		String a[] = time.split(":");
		return ((Long.parseLong(a[0]) * 60 + Long.parseLong(a[1])) * 60 + Long.parseLong(a[2])) * 1000;
	}

	public Map<BeaconAssociateLocation, Long> getAllSavedScannedData() {
		// TODO Auto-generated method stub
		return associateLocationMap;
	}

}
