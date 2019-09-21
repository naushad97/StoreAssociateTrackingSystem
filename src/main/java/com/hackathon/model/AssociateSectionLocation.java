package com.hackathon.model;

public class AssociateSectionLocation {

	private int associateId;
	private int sectionId;

	public int getAssociateId() {
		return associateId;
	}

	public void setAssociateId(int associateId) {
		this.associateId = associateId;
	}

	public int getSectionId() {
		return sectionId;
	}

	public void setSectionId(int sectionId) {
		this.sectionId = sectionId;
	}

	@Override
	public String toString() {
		return this.associateId + ":" + this.sectionId;
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
