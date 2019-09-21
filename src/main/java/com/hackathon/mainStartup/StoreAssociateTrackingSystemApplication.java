package com.hackathon.mainStartup;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hackathon"})
public class StoreAssociateTrackingSystemApplication {

	public static void main(String[] args) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
		System.out.println(System.currentTimeMillis());
    	System.out.println(dateFormat.format(new Date(System.currentTimeMillis())));
		SpringApplication.run(StoreAssociateTrackingSystemApplication.class, args);
	}

}
