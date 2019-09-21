package com.hackathon.validation;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;

@Component
public class StoreAssociateTrackingValidation {

	public boolean validateInputReq(TrackLocationByTimeReq trackLocationByTimeReq,
			TrackLocationByTimeRsp trackLocationByTimeRsp) {
		if (trackLocationByTimeReq == null) {
			populateErrorMsg(trackLocationByTimeRsp, "Req Body should not be null.");
			return false;
		}

		if (StringUtils.isBlank(trackLocationByTimeReq.getProximityUUID())) {
			populateErrorMsg(trackLocationByTimeRsp, "Proximity UUID should have proper value.");
			return false;
		}

		if (StringUtils.isBlank(trackLocationByTimeReq.getUserId())) {
			populateErrorMsg(trackLocationByTimeRsp, "User Id should have proper value.");
			return false;
		}

		if (StringUtils.isBlank(trackLocationByTimeReq.getScanDateTimeStamp())) {
			populateErrorMsg(trackLocationByTimeRsp, "Date Time should have proper value.");
			return false;
		}
		return true;
	}

	private void populateErrorMsg(TrackLocationByTimeRsp trackLocationByTimeRsp, String msg) {
		trackLocationByTimeRsp.setSuccessfull(false);
		trackLocationByTimeRsp.setMessage(msg);
		trackLocationByTimeRsp.setStatus(404);
	}

}
