package com.hackathon.dto;

import java.io.Serializable;
import java.util.StringJoiner;

public class TrackLocationByTimeReq implements Serializable {

	private static final long serialVersionUID = 1L;
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

	public String getProximityUUID() {
		return proximityUUID;
	}

	public void setProximityUUID(String proximityUUID) {
		this.proximityUUID = proximityUUID;
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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", TrackLocationByTimeReq.class.getSimpleName() + "[", "]").add("major=" + major)
				.add("minor=" + minor).add("power=" + power).add("rssi=" + rssi)
				.add("proximityUUID='" + proximityUUID + "'").add("distanceUnit='" + distanceUnit + "'")
				.add("distance=" + distance).add("proximity='" + proximity + "'")
				.add("scanDateTimeStamp='" + scanDateTimeStamp + "'").add("scanTimeInMillis=" + scanTimeInMillis)
				.add("handshakeTimeInNano=" + handshakeTimeInNano).add("userId='" + userId + "'")
				.add("type='" + type + "'").toString();
	}

}
