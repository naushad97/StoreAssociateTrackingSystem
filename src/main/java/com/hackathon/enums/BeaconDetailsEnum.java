package com.hackathon.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.hackathon.model.BeaconDetails;

public enum BeaconDetailsEnum {

	BEACON1(1, "B1", "94e3aa37-f2e4-459e-b5dc-76ab97ec3939", "Apple"), //
	BEACON2(2, "B2", "UID2", "Apple"), //
	BEACON3(3, "B3", "392008d9-208a-4f48-90de-72b98abacc2b", "Apple"), //
	BEACON4(4, "B4", "UID4", "Apple"), //
	BEACON5(5, "B5", "f0c46299-bdc2-4361-8f90-521926ce50b8", "Apple"); 

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
	
	public static List<BeaconDetails> getAllBeacons(){
		List<BeaconDetails> beaconDetails = new ArrayList<BeaconDetails>();
		for(BeaconDetailsEnum beaconDetailsEnum : BeaconDetailsEnum.values()) {
			beaconDetails.add(new BeaconDetails(beaconDetailsEnum.beaconId, beaconDetailsEnum.getName(), beaconDetailsEnum.getUid(), beaconDetailsEnum.getMake()));
		}
		return beaconDetails;
	}

	/*public static BeaconDetailsEnum findBeaconIDByUID(String uid, ) {
		return Arrays.stream(BeaconDetailsEnum.values()).filter(obj -> uid.equals(obj.getUid())).findFirst()
				.orElse(null);
	}*/

}
