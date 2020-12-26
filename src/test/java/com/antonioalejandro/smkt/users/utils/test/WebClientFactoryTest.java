package com.antonioalejandro.smkt.users.utils.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.reactive.function.client.WebClient;

import com.antonioalejandro.smkt.users.utils.WebClientFactory;


class WebClientFactoryTest {

	@Mock
	private DiscoveryClient client;
	
	@Mock
	private ServiceInstance instance;
	
	private String id = "X";
	private static final int PORT = 9100;
	private static final String HOST = "localhost";
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void test() throws Exception {
		List<ServiceInstance> list = new ArrayList<ServiceInstance>(1);
		list.add(instance);
		when(instance.getHost()).thenReturn(HOST);
		when(instance.getPort()).thenReturn(PORT);
		when(client.getInstances(id)).thenReturn(list);
		String url = WebClientFactory.getURLInstanceService(id, client);
		
		assertEquals(String.format("http://%s:%s", HOST, PORT), url);
		
		WebClient client = WebClientFactory.getWebClient(url);
		assertNotNull(client);
		assertThat(client).isInstanceOf(WebClient.class);
	}
}
