package com.antonioalejandro.smkt.users.utils;

import org.apache.http.entity.ContentType;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

public class WebClientFactory {

	private WebClientFactory() {
	}

	public static WebClient getWebClient(String url, String username, String secret) {
		return WebClient.builder().baseUrl(url)
				.defaultHeader(HttpHeaders.CONTENT_TYPE, ContentType.APPLICATION_FORM_URLENCODED.getMimeType())
				.defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Basic %s", "c21hcnRraXRjaGVuYXBwOjEzMzMx"))
				.build();
		
	}

	public static String getURLInstanceService(String serviceId, DiscoveryClient client) {
		ServiceInstance instanceInfo = client.getInstances(serviceId).get(0);
		return String.format("http://%s:%s", instanceInfo.getHost(), instanceInfo.getPort());
	}
}
