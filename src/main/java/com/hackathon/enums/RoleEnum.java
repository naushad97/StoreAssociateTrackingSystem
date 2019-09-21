package com.hackathon.enums;

public enum RoleEnum {

	ADMIN(1, "Admin"), //
	ASSOCIATE(2, "Associate");
	
	private int roleId;
	private String rollName;
	
	private RoleEnum(int roleId, String rollName) {
		this.roleId = roleId;
		this.rollName = rollName;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getRollName() {
		return rollName;
	}

	public void setRollName(String rollName) {
		this.rollName = rollName;
	}

}
