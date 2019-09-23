package com.hackathon.model;

import com.hackathon.enums.ZoneDetailsEnum;

public class BeaconAssociateLocation {

	private int beaconId;
	private int associateId;
	private ZoneDetails zone;
	private int major;
	private int minor;
	private int power;
	private int rssi;
	private String proximityUUID;
	private String distanceUnit;
	private double distance;
	private String proximity;
	private String scanDateTimeStamp;
	private long scanTimeInMillis;
	private long handshakeTimeInNano;
	private String userId;
	private String type;

	public int getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(int beaconId) {
		this.beaconId = beaconId;
	}

	public int getAssociateId() {
		return associateId;
	}

	public void setAssociateId(int associateId) {
		this.associateId = associateId;
	}

	public ZoneDetails getZone() {
		return zone;
	}

	public void setZone(ZoneDetails zone) {
		this.zone = zone;
	}

	public String getProximityUUID() {
		return proximityUUID;
	}

	public void setProximityUUID(String proximityUUID) {
		this.proximityUUID = proximityUUID;
	}

	public int getMajor() {
		return major;
	}

	public void setMajor(int major) {
		this.major = major;
	}

	public int getMinor() {
		return minor;
	}

	public void setMinor(int minor) {
		this.minor = minor;
	}

	public int getPower() {
		return power;
	}

	public void setPower(int power) {
		this.power = power;
	}

	public int getRssi() {
		return rssi;
	}

	public void setRssi(int rssi) {
		this.rssi = rssi;
	}

	public String getDistanceUnit() {
		return distanceUnit;
	}

	public void setDistanceUnit(String distanceUnit) {
		this.distanceUnit = distanceUnit;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getProximity() {
		return proximity;
	}

	public void setProximity(String proximity) {
		this.proximity = proximity;
	}

	public String getScanDateTimeStamp() {
		return scanDateTimeStamp;
	}

	public void setScanDateTimeStamp(String scanDateTimeStamp) {
		this.scanDateTimeStamp = scanDateTimeStamp;
	}

	public long getScanTimeInMillis() {
		return scanTimeInMillis;
	}

	public void setScanTimeInMillis(long scanTimeInMillis) {
		this.scanTimeInMillis = scanTimeInMillis;
	}

	public long getHandshakeTimeInNano() {
		return handshakeTimeInNano;
	}

	public void setHandshakeTimeInNano(long handshakeTimeInNano) {
		this.handshakeTimeInNano = handshakeTimeInNano;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public boolean equals(Object o) {

		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if ((o instanceof BeaconAssociateLocation) && this.toString().equalsIgnoreCase(o.toString())) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public String toString() {
		return this.getBeaconId() + ":" + this.getAssociateId();
	}

}
