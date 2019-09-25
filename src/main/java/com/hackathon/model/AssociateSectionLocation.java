package com.hackathon.model;

public class AssociateSectionLocation {

	private int associateId;
	private int beaconId;

	public int getAssociateId() {
		return associateId;
	}

	public void setAssociateId(int associateId) {
		this.associateId = associateId;
	}

	public int getBeaconId() {
		return beaconId;
	}

	public void setBeaconId(int beaconId) {
		this.beaconId = beaconId;
	}

	@Override
	public String toString() {
		return this.associateId + ":" + this.beaconId;
	}

	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		}

		if (this == o) {
			return true;
		}

		if ((o instanceof AssociateSectionLocation) && this.toString().equalsIgnoreCase(o.toString())) {
			return true;
		} else {
			return false;
		}
	}

}
