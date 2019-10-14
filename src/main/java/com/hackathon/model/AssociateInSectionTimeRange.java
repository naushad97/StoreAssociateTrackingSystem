package com.hackathon.model;

public class AssociateInSectionTimeRange {

	private String entryTime;
	private String exitTime;
	private String zone;
	private String section;
	private String timeSpent;
	private String dateOfAction;
	private String biconID;
	private String biconName;
	private String empId;
	private String empName;
	private String userId;

	public String getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(String entryTime) {
		this.entryTime = entryTime;
	}

	public String getExitTime() {
		return exitTime;
	}

	public void setExitTime(String exitTime) {
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

	public String getTimeSpent() {
		return timeSpent;
	}

	public void setTimeSpent(String timeSpent) {
		this.timeSpent = timeSpent;
	}

	public String getDateOfAction() {
		return dateOfAction;
	}

	public void setDateOfAction(String dateOfAction) {
		this.dateOfAction = dateOfAction;
	}

	public String getBiconID() {
		return biconID;
	}

	public void setBiconID(String biconID) {
		this.biconID = biconID;
	}

	public String getBiconName() {
		return biconName;
	}

	public void setBiconName(String biconName) {
		this.biconName = biconName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEmpName() {
		return empName;
	}

	public void setEmpName(String empName) {
		this.empName = empName;
	}
	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String toString() {
		return "AssociateInSectionTimeRange [entryTime=" + entryTime + ","
				+ " exitTime=" + exitTime + ", "
				+ "zone=" + zone 
				+ ", section=" + section
				+ ", timeDiff=" + timeSpent 
				+ ", dateOfAction=" + dateOfAction 
				+ ", biconID="+ biconID 
				+ ", userId=" + userId 
				+", biconName=" + biconName 
				+ "]";
	}

}
