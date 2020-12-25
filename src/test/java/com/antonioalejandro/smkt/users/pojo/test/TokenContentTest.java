package com.antonioalejandro.smkt.users.pojo.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.pojo.TokenContent;

class TokenContentTest {
	
	@Test
	void test() throws Exception {
		TokenContent token = new TokenContent();
		
		assertNotNull(token);
	}
}
