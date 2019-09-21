package com.hackathon.enums;

import java.util.Arrays;

public enum ZoneDetailsEnum {

	ZONE1(1, "Z1", 1, "Sec1"), //
	ZONE2(2, "Z2", 2, "Sec2"), //
	ZONE3(3, "Z3", 3, "Sec3"), //
	ZONE4(4, "Z4", 4, "Sec4"), //
	ZONE5(5, "Z5", 5, "Sec5");

	private int zoneId;
	private String zoneName;
	private int biconId;
	private String section;

	private ZoneDetailsEnum(int zoneId, String zoneName, int biconId, String section) {
		this.zoneId = zoneId;
		this.zoneName = zoneName;
		this.biconId = biconId;
		this.section = section;
	}

	public int getZoneId() {
		return zoneId;
	}

	public String getZoneName() {
		return zoneName;
	}

	public int getBiconId() {
		return biconId;
	}

	public String getSection() {
		return section;
	}

	public static boolean checkByBiconId(String uid) {
		return BeaconDetailsEnum.checkByUID(uid) ? Arrays.stream(ZoneDetailsEnum.values())
				.anyMatch((t) -> t.getBiconId() == BeaconDetailsEnum.findBeaconIDByUID(uid).getBeaconId()) : false;
	}

	public static ZoneDetailsEnum findZoneByBiconId(String biconId) {
		return Arrays.stream(ZoneDetailsEnum.values())
				.filter(obj -> obj.getBiconId() == BeaconDetailsEnum.findBeaconIDByUID(biconId).getBeaconId())
				.findFirst().orElse(null);
	}
	
	public static ZoneDetailsEnum findZoneBySectionId(String zone) {
		return Arrays.stream(ZoneDetailsEnum.values())
				.filter(obj -> String.valueOf(obj.getZoneId()).equalsIgnoreCase(zone))
				.findFirst().orElse(null);
	}

}
