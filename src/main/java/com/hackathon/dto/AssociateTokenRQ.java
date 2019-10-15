package com.hackathon.dto;

import java.io.Serializable;
import java.util.Map;

public class AssociateTokenRQ implements Serializable {

	private static final long serialVersionUID = 1L;
	private Map<String, String> requestData;

	public Map<String, String> getRequestData() {
		return requestData;
	}

	public void setRequestData(Map<String, String> requestData) {
		this.requestData = requestData;
	}

}
