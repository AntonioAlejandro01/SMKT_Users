package com.antonioalejandro.smkt.users.model.response;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class GenericResponseTest {
	
	@Test
	void test() throws Exception {
		GenericResponse response = new GenericResponse(null, null);
		
		assertNull(response.getHttpStatus());
		assertNull(response.getStatus());
		assertNull(response.getMessage());
		assertTrue(response.haveData());
		
		response = new GenericResponse(HttpStatus.OK,"OK");
		
		assertNotNull(response.getStatus());
		assertFalse(response.haveData());
	}
}
