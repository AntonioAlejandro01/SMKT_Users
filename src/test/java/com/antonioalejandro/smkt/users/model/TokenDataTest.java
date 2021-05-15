package com.antonioalejandro.smkt.users.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class TokenDataTest {
	
	@Test
	void test() throws Exception {
		TokenData token = new TokenData();
		
		assertNotNull(token);
		assertNull(token.getAuthorities());
		assertNull(token.getClientId());
		assertNull(token.getEmail());
		assertNull(token.getExp());
		assertNull(token.getJti());
		assertNull(token.getLastname());
		assertNull(token.getName());
		assertNull(token.getScope());
		assertNull(token.getUsername());
		assertNull(token.getUsernameC());
		assertFalse(token.isActive());
	}
}
