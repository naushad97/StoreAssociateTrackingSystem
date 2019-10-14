package com.hackathon.model;

import com.hackathon.enums.AssociateDetailsEnum;
import com.hackathon.enums.BeaconDetailsEnum;
import com.hackathon.enums.ZoneDetailsEnum;

import java.util.List;
import java.util.logging.Logger;

public class InMemoryData {

    private static Logger logger = Logger.getLogger(InMemoryData.class.getName());

    public static List<AssociateAccountDetails> accountDetails;
    public static List<ZoneDetails> zoneDetails;
    public static List<BeaconDetails> beaconDetails;

    public static AssociateAccountDetails findAssociateByAppSID(String appSID) {
        if(accountDetails == null){
            logger.info("Getting static Data");
            AssociateDetailsEnum associate = AssociateDetailsEnum.findAssociateByASID(appSID);
            if(associate != null) {
            	return new AssociateAccountDetails(associate.getAssociateId(), associate.getName(), associate.getAsid(), associate.getUserId(), associate.getUserPw(), associate.getRoleId());
            }
            return new AssociateAccountDetails(0, "Account Not found");
        }

        logger.info("InMemoryData="+accountDetails.toString());

        logger.info("Getting s3 Data");
        return accountDetails.stream().filter(obj -> obj.getAppSId().equalsIgnoreCase(appSID)).findFirst()
                .orElse(null);
    }

    public static AssociateAccountDetails findAssociateByAssociateId(int id) {
        if(accountDetails == null){
            logger.info("Getting static Data");
            AssociateDetailsEnum associate = AssociateDetailsEnum.findAssociateByAssociateId(id);
            if(associate != null) {
            	return new AssociateAccountDetails(associate.getAssociateId(), associate.getName(), associate.getAsid(), associate.getUserId(), associate.getUserPw(), associate.getRoleId());
            }
            return new AssociateAccountDetails(0, "Account Not found");
        }

        logger.info("Getting s3 Data");
        return accountDetails.stream().filter(obj -> id == obj.getAssociateId()).findFirst()
                .orElse(null);
    }

    public static BeaconDetails findBeaconByBeaconId(int id) {
        if(beaconDetails == null){
            logger.info("Getting static Data");
            BeaconDetailsEnum beacon = BeaconDetailsEnum.findBeaconByBeaconId(id);
            return new BeaconDetails(beacon.getBeaconId(), beacon.getName(), beacon.getUid(), beacon.getMake());
        }

        logger.info("Getting s3 Data");
        return beaconDetails.stream().filter(obj -> id == obj.getBeaconId()).findFirst()
                .orElse(null);
    }

    public static BeaconDetails findBeaconDtlsByUuID(String uuid) {
        if(beaconDetails == null){
            logger.info("Getting static Data");
            BeaconDetailsEnum beacon = BeaconDetailsEnum.findBeaconIDByUID(uuid);
            return new BeaconDetails(beacon.getBeaconId(), beacon.getName(), beacon.getUid(), beacon.getMake());
        }

        logger.info("Getting s3 Data");
        return beaconDetails.stream().filter(obj -> uuid.equals(obj.getUuid())).findFirst()
                .orElse(null);
    }

    public static ZoneDetails findZoneByBeaconUuId(String uuid) {
        if(zoneDetails == null){
            logger.info("Getting static Data");
            ZoneDetailsEnum zone = ZoneDetailsEnum.findZoneByBiconId(uuid);
            return new ZoneDetails(zone.getZoneId(), zone.getBeaconId(), zone.getZoneName(), zone.getSection());
        }

        logger.info("Getting s3 Data");
        BeaconDetails beacon = findBeaconDtlsByUuID(uuid);
        return zoneDetails.stream()
                .filter(obj -> obj.getBeaconId() == beacon.getBeaconId())
                .findFirst().orElse(null);
    }

    public static boolean isValidUser(String userId){
        return (findAssociateByAppSID(userId) != null);
    }
    public static boolean isValidBeaconUuId(String uuid){
        return (findBeaconDtlsByUuID(uuid) !=null);
    }

    public static boolean isZoneExistsByBeaconUuiD(String uuid){
        return (findZoneByBeaconUuId(uuid) !=null);
    }
}
