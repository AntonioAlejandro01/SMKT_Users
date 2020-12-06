package com.antonioalejandro.smkt.users.pojo.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.pojo.TokenContent;

class TokenContentTest {
	
	@Test
	void test() throws Exception {
		TokenContent token = new TokenContent();
		
		assertEquals(2L, token.getId());
		assertEquals("", token.getToken());
		token = new TokenContent("Token");
		
		assertNull(token.getId());
		assertEquals("Token", token.getToken()); 
	}
}
