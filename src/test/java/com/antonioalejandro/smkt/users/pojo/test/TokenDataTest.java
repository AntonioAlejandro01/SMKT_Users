package com.antonioalejandro.smkt.users.pojo.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.pojo.TokenData;

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
