package com.hackathon.dto;

import java.io.Serializable;

public class AssociateLogin extends BaseRsp implements Serializable {

    private int associateId;
    private String name;
    private String appSId;
    private String userId;
    private int rollId;

    public AssociateLogin(){}

    public AssociateLogin(int status, String message) {
        super(status, message);
    }

    public AssociateLogin(int associateId, String name, String appSId, String userId, int rollId, int status, String message) {
        super(status, message);
        this.associateId = associateId;
        this.name = name;
        this.appSId = appSId;
        this.userId = userId;
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

    public int getRollId() {
        return rollId;
    }

    public void setRollId(int rollId) {
        this.rollId = rollId;
    }
}
