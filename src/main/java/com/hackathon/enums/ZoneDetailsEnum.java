package com.hackathon.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hackathon.model.ZoneDetails;

public enum ZoneDetailsEnum {

	ZONE1(1, "Grocery", 1, "Sec1"), //
	ZONE2(2, "Clothings", 2, "Sec2"), //
	ZONE3(3, "Electronics", 3, "Sec3"), //
	ZONE4(4, "Cosmetics", 4, "Sec4"), //
	ZONE5(5, "Furniture", 5, "Sec5");

	private int zoneId;
	private String zoneName;
	private int beaconId;
	private String section;

	private ZoneDetailsEnum(int zoneId, String zoneName, int beaconId, String section) {
		this.zoneId = zoneId;
		this.zoneName = zoneName;
		this.beaconId = beaconId;
		this.section = section;
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
			zoneDetails.add(new ZoneDetails(zoneDetailsEnum.getZoneId(), zoneDetailsEnum.getBeaconId(), zoneDetailsEnum.getZoneName(), zoneDetailsEnum.getSection()));
		}
		return zoneDetails;
	}


}
