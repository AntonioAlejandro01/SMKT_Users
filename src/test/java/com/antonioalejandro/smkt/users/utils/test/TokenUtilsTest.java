package com.antonioalejandro.smkt.users.utils.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.reactive.function.client.WebClient;

import com.antonioalejandro.smkt.users.pojo.TokenData;
import com.antonioalejandro.smkt.users.utils.TokenUtils;
import com.netflix.discovery.DiscoveryClient;

@RunWith(MockitoJUnitRunner.class)
class TokenUtilsTest {

	@InjectMocks
	private TokenUtils tokenUtils;

	@Mock
	private DiscoveryClient client;
	
	@Mock
	private WebClient webClient;
	
	@Mock
	private TokenData data;

	private String scope = "scope";

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void test() throws Exception {

		List<String> scopes = new ArrayList<String>();
		scopes.add(scope);

		when(data.getScope()).thenReturn(scopes);

		List<String> list = new ArrayList<>();
		list.add("scope");

		assertTrue(tokenUtils.isAuthorized(list, data));

		list.clear();
		list.add("X");

		assertFalse(tokenUtils.isAuthorized(list, data));
		assertFalse(tokenUtils.isAuthorized(new ArrayList<String>(), data));
		assertFalse(tokenUtils.isAuthorized(null, data));
		

	}

}
