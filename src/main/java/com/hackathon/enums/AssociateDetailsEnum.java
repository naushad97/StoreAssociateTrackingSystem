package com.hackathon.enums;

import java.util.Arrays;

public enum AssociateDetailsEnum {

	PRITHWISH(1, "Prithwish Ghosh", "ASID1", "pg", "12345", 1), //
	NAUSHAD(2, "MD Naushad Alam", "ASID2", "mna", "12345", 2), //
	MANDEEP(3, "Mandeep Ajmani", "ASID3", "ma", "12345", 2), //
	SUVADIP(4, "Suvadip Roy Chowdhury", "ASID4", "src", "12345", 2), //
	PRITHWIRAJ(5, "Prithwiraj Dey Sikder", "ASID5", "pds", "12345", 2);

	private int associateId;
	private String name;
	private String asid;
	private String userId;
	private String userPw;
	private int roleId;

	private AssociateDetailsEnum(int associateId, String name, String asid, String userId, String userPw, int roleId) {
		this.associateId = associateId;
		this.name = name;
		this.asid = asid;
		this.userId = userId;
		this.userPw = userPw;
		this.roleId = roleId;
	}

	public int getAssociateId() {
		return associateId;
	}

	public String getName() {
		return name;
	}

	public String getAsid() {
		return asid;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserPw() {
		return userPw;
	}

	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public static boolean checkByASID(String asid) {
		return Arrays.stream(AssociateDetailsEnum.values()).anyMatch((t) -> t.getAsid().equals(asid));
	}

	public static AssociateDetailsEnum findAssociateByASID(String asid) {

		System.out.println("asid =" + asid);
		return Arrays.stream(AssociateDetailsEnum.values()).filter(obj -> asid.equals(obj.getAsid())).findFirst()
				.orElse(null);
	}

	public static AssociateDetailsEnum findAssociateByAssociateId(int id) {
		return Arrays.stream(AssociateDetailsEnum.values()).filter(obj -> id == obj.getAssociateId()).findFirst()
				.orElse(null);
	}
}
