package com.hackathon.controller;

import java.util.List;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hackathon.dto.LocationOfAssociateRsp;
import com.hackathon.model.AssociateAccountDetails;
import com.hackathon.model.ZoneDetails;
import com.hackathon.service.StoreAssociateTrackingService;

@Controller
@CrossOrigin
@RequestMapping("/associateWebTracking")
@Scope("request")
public class WebViewController {

	private static Logger logger = Logger.getLogger(WebViewController.class.getName());

	@Autowired
	private StoreAssociateTrackingService storeAssociateTrackingServiceImpl;

	@GetMapping("/getDashboard")
	public String getDashboard(Model model) {
		return "dashboard";
	}

	@PostMapping("/doWebLogin")
	public String adminLogin(@Valid @ModelAttribute AssociateAccountDetails associateAccountDetails, BindingResult bindingResult, Model model) {

		if(bindingResult.hasErrors()) {
			return "user-login";
		}
		
		storeAssociateTrackingServiceImpl.loadAWSDataIntoMemory();
		

		AssociateAccountDetails loggedIn = storeAssociateTrackingServiceImpl.userLogin(associateAccountDetails);
		model.addAttribute("associateAccountDetails", loggedIn);
		if (loggedIn.getStatus() == 1 && loggedIn.getRoleId() != 1) {
			loggedIn = new AssociateAccountDetails();
			loggedIn.setStatus(0);
			loggedIn.setMessage("You don't have sufficient privilege to view the page");

			return "user-login";
		}
		
		if(loggedIn.getStatus() == 0) {
			return "user-login";
		}

		/*
		 * LocationOfAssociateRsp locationOfAssociateRsp =
		 * storeAssociateTrackingServiceImpl.getAllLocationOfAssociate();
		 * if(locationOfAssociateRsp.getLocationAndAssociateDetailsList() != null &&
		 * !locationOfAssociateRsp.getLocationAndAssociateDetailsList().isEmpty()) {
		 * 
		 * }
		 */
		List<ZoneDetails> zoneDetails = storeAssociateTrackingServiceImpl.getZoneDetails();
		for(ZoneDetails zone : zoneDetails) {
			LocationOfAssociateRsp locationMapping = storeAssociateTrackingServiceImpl.getAllLocationOfAssociate(String.valueOf(zone.getZoneId()));
			if(locationMapping.getLocationAndAssociateDetailsList() != null && !locationMapping.getLocationAndAssociateDetailsList().isEmpty()) {
				zone.setAssociateCount(locationMapping.getLocationAndAssociateDetailsList().size());
			}
		}
		model.addAttribute("zoneDetails", zoneDetails);
		
		return "dashboard";
	}

	@GetMapping("/loadLoginPage")
	public String showLogin(AssociateAccountDetails associateAccountDetails) {
		return "user-login";
	}
	
	@GetMapping("/doLogout")
	public String doLogout(AssociateAccountDetails associateAccountDetails) {
		return "user-login";
	}

	@GetMapping("/")
	public String main(Model model) {
		return "user-login";
	}
}
