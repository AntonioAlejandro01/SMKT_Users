package com.antonioalejandro.smkt.users.model.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

class ScopeResponseTest {
	
	@Test
	void test() throws Exception {
		ScopeResponse response = new ScopeResponse(null, null, new ArrayList<String>());
		
		assertThat(response.getScopes()).isInstanceOf(ArrayList.class);
		
		response = new ScopeResponse(null, null);
		
		assertNull(response.getScopes());
		
		response = new ScopeResponse(new ArrayList<String>());
		
		assertThat(response.getScopes()).isInstanceOf(ArrayList.class);
		
	}
}
