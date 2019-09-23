package com.hackathon.controller;

import com.hackathon.dto.*;
import com.hackathon.model.AssociateAccountDetails;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.model.BeaconDetails;
import com.hackathon.model.ZoneDetails;
import com.hackathon.service.StoreAssociateTrackingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@RestController
@CrossOrigin
@RequestMapping(value = "/storeAssociateTracking")
public class StoreAssociateTrackingController {

    private static Logger logger = Logger.getLogger(StoreAssociateTrackingController.class.getName());

    @Autowired
    StoreAssociateTrackingService storeAssociateTrackingServiceImpl;

    @RequestMapping(method = RequestMethod.POST, value = "/doLogin")
    AssociateLogin doLogin(@RequestBody AssociateAccountDetails associateAccount) {

        logger.info("ReqData="+associateAccount.toString());

        storeDataIntoMemory();

        return storeAssociateTrackingServiceImpl.doLogin(associateAccount);
    }

    @RequestMapping(method = RequestMethod.POST, value = "/saveBeaconData")
    TrackLocationByTimeRsp saveBeaconData(@RequestBody TrackLocationByTimeReq trackLocationByTimeReq) {

        storeDataIntoMemory();

        System.out.println("trackLocationByTimeReq : uid = " + trackLocationByTimeReq);

        TrackLocationByTimeRsp trackLocationByTimeRsp = storeAssociateTrackingServiceImpl.trackLocationByTime(trackLocationByTimeReq);

        if (trackLocationByTimeRsp.isSuccessfull()) {
            System.out.println("Sucessfully done");
        } else {
            System.out.println("Failed");
        }

        return trackLocationByTimeRsp;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllAssociateLocation")
    LocationOfAssociateRsp getAllLocationOfAssociate() {

        storeDataIntoMemory();

        LocationOfAssociateRsp locationOfAssociateRsp = storeAssociateTrackingServiceImpl.getAllLocationOfAssociate();

        return locationOfAssociateRsp;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAllAssociateTrackingData")
    Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData() {

        storeDataIntoMemory();

        return storeAssociateTrackingServiceImpl.getAllAssociateTrackingData();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getAssociateDetails")
    List<AssociateAccountDetails> getAssociateAccounts() {
        return storeAssociateTrackingServiceImpl.getAssociateAccounts();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getBeaconDetails")
    List<BeaconDetails> getBeaconDetails() {
        return storeAssociateTrackingServiceImpl.getBeaconDetails();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/getZoneDetails")
    List<ZoneDetails> getZoneDetails() {
        return storeAssociateTrackingServiceImpl.getZoneDetails();
    }

    private void storeDataIntoMemory(){
        getAssociateAccounts();
        getBeaconDetails();
        getZoneDetails();
    }

}
