package com.hackathon.model;

import java.io.Serializable;
import java.util.StringJoiner;

public class ZoneDetails implements Serializable {

    private int zoneId;
    private int beaconId;
    private String zoneName;
    private String section;

    public ZoneDetails(){}

    public ZoneDetails(int zoneId, int beaconId, String zoneName, String section) {
        this.zoneId = zoneId;
        this.beaconId = beaconId;
        this.zoneName = zoneName;
        this.section = section;
    }

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public String getZoneName() {
        return zoneName;
    }

    public void setZoneName(String zoneName) {
        this.zoneName = zoneName;
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", ZoneDetails.class.getSimpleName() + "[", "]")
                .add("zoneId=" + zoneId)
                .add("zoneName='" + zoneName + "'")
                .add("beaconId='" + beaconId + "'")
                .add("section='" + section + "'")
                .toString();
    }
}
