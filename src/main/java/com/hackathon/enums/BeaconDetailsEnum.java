package com.hackathon.enums;

import com.hackathon.model.BeaconDetails;

import java.util.Arrays;
import java.util.List;

public enum BeaconDetailsEnum {

	BEACON1(1, "B1", "94e3aa37-f2e4-459e-b5dc-76ab97ec3939", "Apple"), //
	BEACON2(2, "B2", "UID2", "Apple"), //
	BEACON3(3, "B3", "UID3", "Apple"), //
	BEACON4(4, "B4", "UID4", "Apple"), //
	BEACON5(5, "B5", "UID5", "Apple");

	private int beaconId;
	private String name;
	private String uid;
	private String make;

	private BeaconDetailsEnum(int beaconId, String name, String uid, String make) {
		this.beaconId = beaconId;
		this.name = name;
		this.uid = uid;
		this.make = make;
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

	public String getMake() {
		return make;
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

	/*public static BeaconDetailsEnum findBeaconIDByUID(String uid, ) {
		return Arrays.stream(BeaconDetailsEnum.values()).filter(obj -> uid.equals(obj.getUid())).findFirst()
				.orElse(null);
	}*/

}
