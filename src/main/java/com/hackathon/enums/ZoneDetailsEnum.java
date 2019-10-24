package com.hackathon.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hackathon.model.ZoneDetails;

public enum ZoneDetailsEnum {

	ZONE1(1, "Grocery", 1, "Sec1", 7), //
	ZONE2(2, "Clothings", 2, "Sec2", 4), //
	ZONE3(3, "Electronics", 3, "Sec3", 15), //
	ZONE4(4, "Cosmetics", 4, "Sec4", 2), //
	ZONE5(5, "Furniture", 5, "Sec5", 5);

	private int zoneId;
	private String zoneName;
	private int beaconId;
	private String section;
	private int crowdCount;

	private ZoneDetailsEnum(int zoneId, String zoneName, int beaconId, String section, int crowdCount) {
		this.zoneId = zoneId;
		this.zoneName = zoneName;
		this.beaconId = beaconId;
		this.section = section;
		this.crowdCount = crowdCount;
	}

	public int getZoneId() {
		return zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public int getBeaconId() {
		return beaconId;
	}

	public String getSection() {
		return section;
	}

	public int getCrowdCount() {
		return crowdCount;
	}

	public void setZoneId(int zoneId) {
		this.zoneId = zoneId;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public void setBeaconId(int beaconId) {
		this.beaconId = beaconId;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public void setCrowdCount(int crowdCount) {
		this.crowdCount = crowdCount;
	}

	public static boolean checkByBiconId(String uid) {
		return BeaconDetailsEnum.checkByUID(uid) ? Arrays.stream(ZoneDetailsEnum.values())
				.anyMatch((t) -> t.getBeaconId() == BeaconDetailsEnum.findBeaconIDByUID(uid).getBeaconId()) : false;
	}

	public static ZoneDetailsEnum findZoneByBiconId(String beaconId) {
		return Arrays.stream(ZoneDetailsEnum.values())
				.filter(obj -> obj.getBeaconId() == BeaconDetailsEnum.findBeaconIDByUID(beaconId).getBeaconId())
				.findFirst().orElse(null);
	}
	
	public static ZoneDetailsEnum findZoneBySectionId(String zone) {
		return Arrays.stream(ZoneDetailsEnum.values())
				.filter(obj -> String.valueOf(obj.getZoneId()).equalsIgnoreCase(zone))
				.findFirst().orElse(null);
	}
	
	public static ZoneDetailsEnum findZoneByZoneId(String zoneId) {
		return Arrays.stream(ZoneDetailsEnum.values())
				.filter(obj -> String.valueOf(obj.getZoneId()).equalsIgnoreCase(zoneId))
				.findFirst().orElse(null);
	}
	
	public static List<ZoneDetails>  getAllZoneDetails(){
		List<ZoneDetails> zoneDetails = new ArrayList<ZoneDetails>();
		for(ZoneDetailsEnum zoneDetailsEnum : ZoneDetailsEnum.values()) {
			zoneDetails.add(new ZoneDetails(zoneDetailsEnum.getZoneId(), zoneDetailsEnum.getBeaconId(), zoneDetailsEnum.getZoneName(), zoneDetailsEnum.getSection(), zoneDetailsEnum.getCrowdCount()));
		}
		return zoneDetails;
	}
}
