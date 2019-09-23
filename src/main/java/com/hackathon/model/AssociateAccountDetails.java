package com.hackathon.model;

import java.io.Serializable;
import java.util.StringJoiner;

public class AssociateAccountDetails implements Serializable {

    private int associateId;
    private String name;
    private String appSId;
    private String userId;
    private String userPw;
    private int rollId;

    public AssociateAccountDetails(){}

    public AssociateAccountDetails(int associateId, String name, String asid, String userId, String userPw, int rollId) {
        this.associateId = associateId;
        this.name = name;
        this.appSId = asid;
        this.userId = userId;
        this.userPw = userPw;
        this.rollId = rollId;
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

    public int getRollId() {
        return rollId;
    }

    public void setRollId(int rollId) {
        this.rollId = rollId;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AssociateAccountDetails.class.getSimpleName() + "[", "]")
                .add("associateId='" + associateId + "'")
                .add("name='" + name + "'")
                .add("asid='" + appSId + "'")
                .add("userId='" + userId + "'")
                .add("userPw='" + userPw + "'")
                .add("rollId='" + rollId + "'")
                .toString();
    }
}
