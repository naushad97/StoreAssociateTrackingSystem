package com.hackathon.dto;

import java.io.Serializable;

public class PushNotificationReq implements Serializable {


	private static final long serialVersionUID = 1L;
	
	private PushNotification notification;
	private Data data;
	private String to;
	private String priority;
	
	public PushNotification getNotification() {
		return notification;
	}
	public void setNotification(PushNotification notification) {
		this.notification = notification;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getTo() {
		return to;
	}
	public void setTo(String to) {
		this.to = to;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	@Override
	public String toString() {
		return "PushNotificationReq [notification=" + notification + ", data=" + data + ", to=" + to + ", priority="
				+ priority + "]";
	}

}
