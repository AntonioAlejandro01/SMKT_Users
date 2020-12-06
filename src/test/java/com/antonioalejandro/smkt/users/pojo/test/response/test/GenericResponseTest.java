package com.antonioalejandro.smkt.users.pojo.test.response.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.pojo.response.GenericResponse;

class GenericResponseTest {
	
	@Test
	void test() throws Exception {
		GenericResponse response = new GenericResponse(null, null);
		
		assertNull(response.getHttpStatus());
		assertNull(response.getStatus());
		assertNull(response.getMessage());
		assertTrue(response.haveData());
	}
}
