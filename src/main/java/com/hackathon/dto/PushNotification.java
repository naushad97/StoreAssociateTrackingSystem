package com.hackathon.dto;

import java.io.Serializable;

public class PushNotification implements Serializable {

	private static final long serialVersionUID = 1L;

	private String title;
	private String body;
	private String message;
	private String sound;
	private String clickAction;
	private String icon;

	public PushNotification() {
	}

	public PushNotification(String title, String body, String message, String sound, String clickAction, String icon) {
		this.title = title;
		this.body = body;
		this.message = message;
		this.sound = sound;
		this.clickAction = clickAction;
		this.icon = icon;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getClickAction() {
		return clickAction;
	}

	public void setClickAction(String clickAction) {
		this.clickAction = clickAction;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "PushNotification [title=" + title + ", body=" + body + ", message=" + message + ", sound=" + sound
				+ ", clickAction=" + clickAction + ", icon=" + icon + "]";
	}
	
	

}
