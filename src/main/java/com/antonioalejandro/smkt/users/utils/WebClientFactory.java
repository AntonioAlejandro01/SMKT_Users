package com.antonioalejandro.smkt.users.utils;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClient Factory Class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
public class WebClientFactory {

	/**
	 * Instantiates a new web client factory.
	 */
	private WebClientFactory() {
	}

	/**
	 * Gets the web client.
	 *
	 * @param url the url
	 * @return the web client
	 */
	public static WebClient getWebClient(String url) {
		return WebClient.builder().baseUrl(url)
				.defaultHeader(HttpHeaders.AUTHORIZATION, String.format("Basic %s", "c21hcnRraXRjaGVuYXBwOjEzMzMx"))
				.build();

	}

	/**
	 * Gets the URL instance service.
	 *
	 * @param serviceId the service id
	 * @param client    the client
	 * @return the URL instance service
	 */
	public static String getURLInstanceService(String serviceId, DiscoveryClient client) {
		ServiceInstance instanceInfo = client.getInstances(serviceId).get(0);
		return String.format("http://%s:%s", instanceInfo.getHost(), instanceInfo.getPort());
	}
}
