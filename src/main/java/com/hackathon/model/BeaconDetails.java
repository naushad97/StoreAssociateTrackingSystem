package com.hackathon.model;

import java.io.Serializable;
import java.util.StringJoiner;

public class BeaconDetails implements Serializable {

    private int beaconId;
    private String name;
    private String uuid;
    private String make;

    public BeaconDetails(int beaconId, String name, String uid, String make) {
        this.beaconId = beaconId;
        this.name = name;
        this.uuid = uid;
        this.make = make;
    }

    public int getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(int beaconId) {
        this.beaconId = beaconId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", BeaconDetails.class.getSimpleName() + "[", "]")
                .add("beaconId='" + beaconId + "'")
                .add("name='" + name + "'")
                .add("uuid='" + uuid + "'")
                .add("make='" + make + "'")
                .toString();
    }
}
