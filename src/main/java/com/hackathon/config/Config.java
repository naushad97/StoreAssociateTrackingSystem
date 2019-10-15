package com.hackathon.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class Config {

	@Bean
	public Map<String, String> associateDeviceMapping() {
		return new ConcurrentHashMap<>();
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}