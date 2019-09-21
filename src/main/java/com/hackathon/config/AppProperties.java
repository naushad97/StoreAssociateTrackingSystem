package com.hackathon.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("app")
public class AppProperties {

	private Boolean databaseCall;
	private int interval;

	public Boolean getDatabaseCall() {
		return databaseCall;
	}

	public void setDatabaseCall(Boolean databaseCall) {
		this.databaseCall = databaseCall;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

}
