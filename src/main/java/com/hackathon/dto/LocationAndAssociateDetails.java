package com.hackathon.dto;

import java.io.Serializable;

public class LocationAndAssociateDetails implements Serializable {

	private static final long serialVersionUID = 1L;
	private String zoneName;
	private String biconName;
	private String sectionName;
	private long time;
	private String associateName;
	private String associateAsid;
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

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public String getBiconName() {
		return biconName;
	}

	public void setBiconName(String biconName) {
		this.biconName = biconName;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getAssociateName() {
		return associateName;
	}

	public void setAssociateName(String associateName) {
		this.associateName = associateName;
	}

	public String getAssociateAsid() {
		return associateAsid;
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

	public void setAssociateAsid(String associateAsid) {
		this.associateAsid = associateAsid;
	}

}
