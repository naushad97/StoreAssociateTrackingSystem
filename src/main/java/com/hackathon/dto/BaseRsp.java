package com.hackathon.dto;

import java.io.Serializable;
import java.util.StringJoiner;

public class BaseRsp implements Serializable {

	private static final long serialVersionUID = 1L;
	private int status;
	private String message;

	public BaseRsp(){}

	public BaseRsp(int status, String message) {
		this.status = status;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", BaseRsp.class.getSimpleName() + "[", "]").add("status=" + status)
				.add("message='" + message + "'").toString();
	}
}
