package com.hackathon.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackathon.config.AppProperties;
import com.hackathon.dto.AssociateLogin;
import com.hackathon.dto.AssociateRelocationRQ;
import com.hackathon.dto.AssociateTokenRQ;
import com.hackathon.dto.Data;
import com.hackathon.dto.DeviceRegistrationReq;
import com.hackathon.dto.LocationAndAssociateDetails;
import com.hackathon.dto.LocationOfAssociateRsp;
import com.hackathon.dto.PushNotification;
import com.hackathon.dto.PushNotificationReq;
import com.hackathon.dto.TrackLocationByTimeReq;
import com.hackathon.dto.TrackLocationByTimeRsp;
import com.hackathon.enums.AssociateDetailsEnum;
import com.hackathon.enums.ZoneDetailsEnum;
import com.hackathon.model.AssociateAccountDetails;
import com.hackathon.model.AssociateInSectionTimeRange;
import com.hackathon.model.BeaconDetails;
import com.hackathon.model.InMemoryData;
import com.hackathon.model.ZoneDetails;
import com.hackathon.process.LocationAllocationEnumDataProcessImpl;
import com.hackathon.validation.StoreAssociateTrackingValidation;

@Component
public class StoreAssociateTrackingServiceImpl implements StoreAssociateTrackingService {

	private static Logger logger = Logger.getLogger(StoreAssociateTrackingServiceImpl.class.getName());
	private static final String PUSH_NOTIFICATION_MESSAGE = "Hi %s! Please relocate to zone= %s to serve the customer there.";
	private static final String FCM_SERVER_KEY = "AAAAAW5S9Qc:APA91bFgG5TXiO2mlpOS4O0NePD3cOtyf3wICoBX2ZeB9LFZEePO0IZ2Jcjhw8b48wo9mHb4yZRCGLnRZVQEjt5aNCR2_nL-APQ9vcMnxWl-W4H5zhmMhZ9_juyQGljtbKfMYlWjvnnj";
	private static final String FCM_TO_TOKEN = "fEc7CWCPPLs:APA91bEDmVc8BMQsdA2wdiWuhevgwe97cqhFzyre4wbe0-lB9YR5exYdf83f2c3qibfs90iKkiFME0q9NGMNIPMfDW2svyv-Ga78rtSuffblQRRxkJ_NaSlofHyTcjobT_0OKZswodqy";

	@Autowired
	private StoreAssociateTrackingValidation storeAssociateTrackingValidation;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private LocationAllocationEnumDataProcessImpl locationAllocationEnumDataProcessImpl;

	private ObjectMapper objectMapper = new ObjectMapper();

	@Resource
	private Map<String, String> associateDeviceMapping;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public AssociateLogin doLogin(AssociateAccountDetails associateAccountDetails) {
		AssociateAccountDetails accountFound = InMemoryData.findAssociateByAppSID(associateAccountDetails.getAppSId());

		if (accountFound != null) {
			logger.info("FilteredResult=" + accountFound.toString());
			if (accountFound.getUserPw().equalsIgnoreCase(associateAccountDetails.getUserPw())) {
				return new AssociateLogin(accountFound.getAssociateId(), accountFound.getName(),
						accountFound.getAppSId(), accountFound.getUserId(), accountFound.getRoleId(), 1,
						"Logged In Successfully");
			}
		}

		return new AssociateLogin(0, "Login failed");
	}
	
	@Override
	public AssociateAccountDetails userLogin(AssociateAccountDetails associateAccountDetails) {
		AssociateAccountDetails accountFound = InMemoryData.findAssociateByAppSID(associateAccountDetails.getAppSId());

		if (accountFound != null && accountFound.getStatus() == 1) {
			logger.info("FilteredResult=" + accountFound.toString());
			if (accountFound.getUserPw().equalsIgnoreCase(associateAccountDetails.getUserPw())) {
				accountFound.setStatus(1);
				accountFound.setMessage("Logged In Successfully");
				return accountFound;
			}
		}

		return new AssociateAccountDetails(0, "Login failed");
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

		List<LocationAndAssociateDetails> locationAndAssociateDetailsList = locationAllocationEnumDataProcessImpl.getAllLocationOfAssociate();

		locationOfAssociateRsp.getLocationAndAssociateDetailsList().addAll(locationAndAssociateDetailsList);

		return locationOfAssociateRsp;
	}
	
	@Override
	public LocationOfAssociateRsp getAllLocationOfAssociate(String zoneId) {
		LocationOfAssociateRsp locationOfAssociateRsp = new LocationOfAssociateRsp();

		List<LocationAndAssociateDetails> locationAndAssociateDetailsList = locationAllocationEnumDataProcessImpl.getAllLocationOfAssociate(zoneId);

		locationOfAssociateRsp.getLocationAndAssociateDetailsList().addAll(locationAndAssociateDetailsList);

		return locationOfAssociateRsp;
	}
	
	

	@Override
	public Map<String, List<AssociateInSectionTimeRange>> getAllAssociateTrackingData() {

		return locationAllocationEnumDataProcessImpl.getAllAssociateTrackingData();
	}
	
	@Override
	public void loadAWSDataIntoMemory() {
		getAssociateAccounts();
		getZoneDetails();
		getBeaconDetails();
	}

	@Override
	public List<AssociateAccountDetails> getAssociateAccounts() {
		List<AssociateAccountDetails> associateAccounts = null;
		try {
			InputStream objectStream = getInputStreamFromS3("static/associateAccounts.json");
			if (objectStream != null) {
				associateAccounts = objectMapper.readValue(objectStream,
						new TypeReference<List<AssociateAccountDetails>>() {
						});
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
			if (objectStream != null) {
				zoneDetails = objectMapper.readValue(objectStream, new TypeReference<List<ZoneDetails>>() {
				});
				objectStream.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		InMemoryData.zoneDetails = zoneDetails;
		
		if(InMemoryData.zoneDetails == null) {
			return ZoneDetailsEnum.getAllZoneDetails();
		}
		return zoneDetails;
	}

	@Override
	public List<BeaconDetails> getBeaconDetails() {
		List<BeaconDetails> beaconDetails = null;
		try {

			InputStream objectStream = getInputStreamFromS3("static/beaconDetails.json");
			if (objectStream != null) {
				beaconDetails = objectMapper.readValue(objectStream, new TypeReference<List<BeaconDetails>>() {
				});
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
			/*
			 * AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
			 * .withCredentials(DefaultAWSCredentialsProviderChain.getInstance()).build();
			 * S3Object object = s3Client.getObject(new
			 * GetObjectRequest("/elasticbeanstalk-ap-south-1-564820835441", s)); return
			 * object.getObjectContent();
			 */
		} catch (Exception e) {

		}

		return null;
	}
	
	@Override
	public void setFcmToken(final AssociateTokenRQ associateTokenRQ) {
		for (Map.Entry<String, String> entry : associateTokenRQ.getRequestData().entrySet()) {
			InMemoryData.setfcmDeviceToken(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public void sendNotification(final AssociateRelocationRQ associateRelocationRQ) {
		associateRelocationRQ.getRelocateAssociateReqs().stream().forEach(rq -> {
			final PushNotificationReq notificationReq = new PushNotificationReq();
			notificationReq.setTo(InMemoryData.fcmDeviceToken.get(rq.getAssociateId()));
			notificationReq.setPriority("high");
			
			final Data data = new Data();
			data.setAssociateId(rq.getAssociateId());
			data.setToZoneId(rq.getToZoneId());
			data.setToZoneName(ZoneDetailsEnum.findZoneByZoneId(rq.getToZoneId()).getZoneName());
			
			notificationReq.setData(data);
			
			String message = String.format(PUSH_NOTIFICATION_MESSAGE, AssociateDetailsEnum.findAssociateByASID(rq.getAssociateId()).getName(), ZoneDetailsEnum.findZoneByZoneId(rq.getToZoneId()));
			PushNotification notification = new PushNotification("Zone Allocation", message, message, "default", ".ui.scanner.ScannerActivity", "fcm_push_icon");
			
			notificationReq.setNotification(notification);
		
			final HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
			headers.add("Authorization", "key="+FCM_SERVER_KEY);
			final HttpEntity<PushNotificationReq> requestEntity = new HttpEntity<>(notificationReq, headers);
			
			logger.info("notificationReq="+notificationReq.toString());
						
			restTemplate.exchange("https://fcm.googleapis.com/fcm/send", HttpMethod.POST, requestEntity, Object.class, new HashMap<>());
		});

	}

	@Override
	public void registerMobileRegistrationId(final DeviceRegistrationReq deviceRegistrationReq) {
		this.associateDeviceMapping.put(deviceRegistrationReq.getAssociateId(),
				deviceRegistrationReq.getRegistrationId());
	}

}
