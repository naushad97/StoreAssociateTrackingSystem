package com.hackathon.enums;

import java.util.Arrays;

public enum BeaconDetailsEnum {

	BEACON1(1, "B1", "UID1"), //
	BEACON2(2, "B2", "UID2"), //
	BEACON3(3, "B3", "UID3"), //
	BEACON4(4, "B4", "UID4"), //
	BEACON5(5, "B5", "UID5");

	private int beaconId;
	private String name;
	private String uid;

	private BeaconDetailsEnum(int beaconId, String name, String uid) {
		this.beaconId = beaconId;
		this.name = name;
		this.uid = uid;
	}

	public int getBeaconId() {
		return beaconId;
	}

	public String getName() {
		return name;
	}

	public String getUid() {
		return uid;
	}

	public static boolean checkByUID(String uid) {
		return Arrays.stream(BeaconDetailsEnum.values()).anyMatch((t) -> t.getUid().equals(uid));
	}

	public static BeaconDetailsEnum findBeaconIDByUID(String uid) {
		return Arrays.stream(BeaconDetailsEnum.values()).filter(obj -> uid.equals(obj.getUid())).findFirst()
				.orElse(null);
	}

	public static BeaconDetailsEnum findBeaconByBeaconId(int id) {
		return Arrays.stream(BeaconDetailsEnum.values()).filter(obj -> id == obj.getBeaconId()).findFirst()
				.orElse(null);
	}

}
