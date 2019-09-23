package com.hackathon.service;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.config.AppProperties;
import com.hackathon.dto.*;
import com.hackathon.model.*;
import com.hackathon.process.LocationAllocationEnumDataProcessImpl;
import com.hackathon.validation.StoreAssociateTrackingValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Component
public class StoreAssociateTrackingServiceImpl implements StoreAssociateTrackingService {

    private static Logger logger = Logger.getLogger(StoreAssociateTrackingServiceImpl.class.getName());

    @Autowired
    private StoreAssociateTrackingValidation storeAssociateTrackingValidation;

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private LocationAllocationEnumDataProcessImpl locationAllocationEnumDataProcessImpl;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AssociateLogin doLogin(AssociateAccountDetails associateAccountDetails){
		AssociateAccountDetails accountFound = InMemoryData.findAssociateByAppSID(associateAccountDetails.getAppSId());

        if(accountFound != null){
            logger.info("FilteredResult="+accountFound.toString());
            if(accountFound.getUserPw().equalsIgnoreCase(associateAccountDetails.getUserPw())){
                return new AssociateLogin(accountFound.getAssociateId(), accountFound.getName(), accountFound.getAppSId(), accountFound.getUserId(), accountFound.getRollId(), 1, "Logged In Successfully");
            }
        }

		return new AssociateLogin(0, "Login failed");
	}

    @Override
    public TrackLocationByTimeRsp trackLocationByTime(TrackLocationByTimeReq trackLocationByTimeReq) {

        TrackLocationByTimeRsp trackLocationByTimeRsp = new TrackLocationByTimeRsp();

        if (!storeAssociateTrackingValidation.validateInputReq(trackLocationByTimeReq, trackLocationByTimeRsp)) {
            return trackLocationByTimeRsp;
        } else {
            if (appProperties.getDatabaseCall()) {
                System.out.println("From Database");
            } else {
                trackLocationByTimeRsp.setSuccessfull(locationAllocationEnumDataProcessImpl
                        .setReqDataToMap(trackLocationByTimeRsp, trackLocationByTimeReq));
            }
        }
        return trackLocationByTimeRsp;
    }

    @Override
    public LocationOfAssociateRsp getAllLocationOfAssociate() {
        LocationOfAssociateRsp locationOfAssociateRsp = new LocationOfAssociateRsp();

        List<LocationAndAssociateDetails> locationAndAssociateDetailsList = locationAllocationEnumDataProcessImpl
                .getAllLocationOfAssociate();

        locationOfAssociateRsp.getLocationAndAssociateDetailsList().addAll(locationAndAssociateDetailsList);

        return locationOfAssociateRsp;
    }

    @Override
    public Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData() {

        return locationAllocationEnumDataProcessImpl.getAllAssociateTrackingData();
    }

    @Override
    public List<AssociateAccountDetails> getAssociateAccounts() {
        List<AssociateAccountDetails> associateAccounts = null;
        try {
            InputStream objectStream = getInputStreamFromS3("static/associateAccounts.json");
            if(objectStream != null){
                associateAccounts = objectMapper.readValue(objectStream, new TypeReference<List<AssociateAccountDetails>>(){});
                objectStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

		InMemoryData.accountDetails = associateAccounts;
        return associateAccounts;
    }

	@Override
    public List<ZoneDetails> getZoneDetails() {
        List<ZoneDetails> zoneDetails = null;
        try {
            InputStream objectStream = getInputStreamFromS3("static/zoneDetails.json");
            if(objectStream != null){
                zoneDetails = objectMapper.readValue(objectStream, new TypeReference<List<ZoneDetails>>(){});
                objectStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

		InMemoryData.zoneDetails = zoneDetails;
        return zoneDetails;
    }

	@Override
    public List<BeaconDetails> getBeaconDetails() {
        List<BeaconDetails> beaconDetails = null;
        try {

            InputStream objectStream = getInputStreamFromS3("static/beaconDetails.json");
            if(objectStream != null){
                beaconDetails = objectMapper.readValue(objectStream, new TypeReference<List<BeaconDetails>>(){});
                objectStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

		InMemoryData.beaconDetails = beaconDetails;
        return beaconDetails;
    }

    private InputStream getInputStreamFromS3(String s) {
        try {
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withCredentials(DefaultAWSCredentialsProviderChain.getInstance())
                    .build();
            S3Object object = s3Client.getObject(new GetObjectRequest("/elasticbeanstalk-ap-south-1-564820835441", s));
            return object.getObjectContent();
        } catch (Exception e){

        }

        return null;
    }

}
