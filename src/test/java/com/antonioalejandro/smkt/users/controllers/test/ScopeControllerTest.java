package com.antonioalejandro.smkt.users.controllers.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.antonioalejandro.smkt.users.UtilsForTesting;
import com.antonioalejandro.smkt.users.controllers.ScopesController;
import com.antonioalejandro.smkt.users.pojo.response.ScopeResponse;
import com.antonioalejandro.smkt.users.service.ScopesService;

class ScopeControllerTest {

	@InjectMocks
	private ScopesController controller;

	@Mock
	private ScopesService service;

	private List<String> scopes = createListScopes();

	private final Long MOCK_ID = 1L;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetScopesByRoleIdOk() throws Exception {
		when(service.getScopesForRole(MOCK_ID)).thenReturn(new ScopeResponse(scopes));

		ResponseEntity<ScopeResponse> response = controller.getScopesForRole(MOCK_ID);
		
		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		ScopeResponse scopeResponse = response.getBody();
		assertThat(scopeResponse).isInstanceOf(ScopeResponse.class);
		assertNull(scopeResponse.getHttpStatus());
		assertNull(scopeResponse.getMessage());
		assertNotNull(scopeResponse.getScopes());
		assertThat(scopeResponse.getScopes()).isInstanceOf(List.class);

	}
	@Test
	void testGetScopesByRoleIdFailIdZero() throws Exception {

		ResponseEntity<ScopeResponse> response = controller.getScopesForRole(0L);
		
		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		ScopeResponse scopeResponse = response.getBody();
		
		assertThat(scopeResponse).isInstanceOf(ScopeResponse.class);
		assertNotNull(scopeResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, scopeResponse.getHttpStatus());
		assertNotNull(scopeResponse.getMessage());
		assertFalse(scopeResponse.getMessage().isBlank());
		assertNull(scopeResponse.getScopes());

	}
	@Test
	void testGetScopesByRoleIdFailIdNull() throws Exception {

		ResponseEntity<ScopeResponse> response = controller.getScopesForRole(null);
		
		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		
		ScopeResponse scopeResponse = response.getBody();
		
		assertThat(scopeResponse).isInstanceOf(ScopeResponse.class);
		assertNotNull(scopeResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, scopeResponse.getHttpStatus());
		assertNotNull(scopeResponse.getMessage());
		assertFalse(scopeResponse.getMessage().isBlank());
		assertNull(scopeResponse.getScopes());

	}
	
	@Test
	void testGetScopesByRoleIdFailEmpty() throws Exception {
		when(service.getScopesForRole(MOCK_ID)).thenReturn(new ScopeResponse(HttpStatus.BAD_REQUEST,"X"));

		ResponseEntity<ScopeResponse> response = controller.getScopesForRole(MOCK_ID);
		
		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertNotEquals(HttpStatus.OK, response.getStatusCode());
		
		ScopeResponse scopeResponse = response.getBody();
		
		assertThat(scopeResponse).isInstanceOf(ScopeResponse.class);
		assertNotNull(scopeResponse.getHttpStatus());
		assertNotEquals(HttpStatus.OK, scopeResponse.getHttpStatus());
		assertNotNull(scopeResponse.getMessage());
		assertFalse(scopeResponse.getMessage().isBlank());
		assertNull(scopeResponse.getScopes());

	}

	private List<String> createListScopes() {
		List<String> scopes = new ArrayList<String>();
		scopes.add(UtilsForTesting.DATAOK);
		return scopes;
	}
}
