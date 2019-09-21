package com.hackathon.dto;

import java.io.Serializable;

public class TrackLocationByTimeRsp extends BaseRsp implements Serializable {

	private static final long serialVersionUID = 1L;
	private boolean isSuccessfull;

	public boolean isSuccessfull() {
		return isSuccessfull;
	}

	public void setSuccessfull(boolean isSuccessfull) {
		this.isSuccessfull = isSuccessfull;
	}

}
