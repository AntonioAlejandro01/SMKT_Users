package com.antonioalejandro.smkt.users.pojo.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.pojo.TokenData;

class TokenContentTest {
	
	@Test
	void test() throws Exception {
		TokenData token = new TokenData();
		
		assertNotNull(token);
	}
}
