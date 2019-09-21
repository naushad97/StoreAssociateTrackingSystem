package com.hackathon.process;

import com.hackathon.dto.TrackLocationByTimeRsp;

public interface LocationAllocationMapProcess {

	boolean validateUidAndappSID(TrackLocationByTimeRsp trackLocationByTimeRsp, String uid, String appSID);

}
