package com.hackathon.dto;

import java.io.Serializable;

public class PushNotificationReq implements Serializable {

	private static final long serialVersionUID = 1L;
	private String to;
	private Data data;

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
}
