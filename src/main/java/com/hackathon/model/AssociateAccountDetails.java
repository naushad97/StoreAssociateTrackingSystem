package com.hackathon.model;

import java.io.Serializable;
import java.util.StringJoiner;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hackathon.dto.BaseRsp;

public class AssociateAccountDetails extends BaseRsp implements Serializable {

	private static final long serialVersionUID = 1L;
	private int associateId;
    private String name;
    
    @NotBlank
    private String appSId;
    private String userId;
    
    @NotBlank @JsonIgnore
    private String userPw;
    private int roleId;

    public AssociateAccountDetails(){}
    
    public AssociateAccountDetails(int status, String message) {
        super(status, message);
    }

    public AssociateAccountDetails(int associateId, String name, String asid, String userId, String userPw, int roleId) {
    	super(1, "Accound Found");
    	
        this.associateId = associateId;
        this.name = name;
        this.appSId = asid;
        this.userId = userId;
        this.userPw = userPw;
        this.roleId = roleId;
    }
    
    public AssociateAccountDetails(int associateId, String name, String asid, String userId,  int roleId) {
    	
        this.associateId = associateId;
        this.name = name;
        this.appSId = asid;
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getAssociateId() {
        return associateId;
    }

    public void setAssociateId(int associateId) {
        this.associateId = associateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppSId() {
        return appSId;
    }

    public void setAppSId(String appSId) {
        this.appSId = appSId;
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

    @Override
    public String toString() {
        return new StringJoiner(", ", AssociateAccountDetails.class.getSimpleName() + "[", "]")
                .add("associateId='" + associateId + "'")
                .add("name='" + name + "'")
                .add("asid='" + appSId + "'")
                .add("userId='" + userId + "'")
                .add("userPw='" + userPw + "'")
                .add("roleId='" + roleId + "'")
                .toString();
    }
}
