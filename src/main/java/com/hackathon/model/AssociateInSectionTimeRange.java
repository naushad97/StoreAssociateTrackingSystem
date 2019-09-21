package com.hackathon.model;

public class AssociateInSectionTimeRange {

	private long entryTime;
	private long exitTime;
	private String zone;
	private String section;

	public long getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}

	public long getExitTime() {
		return exitTime;
	}

	public void setExitTime(long exitTime) {
		this.exitTime = exitTime;
	}
	
	public String getZone() {
		return zone;
	}

	public void setZone(String zone) {
		this.zone = zone;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "[entryTime=" + entryTime + ", exitTime=" + exitTime + ", zone=" + zone
				+ ", section=" + section + "]";
	}

	
}
