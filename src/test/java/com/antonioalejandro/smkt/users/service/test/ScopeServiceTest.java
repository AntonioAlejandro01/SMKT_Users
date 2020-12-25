package com.antonioalejandro.smkt.users.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.dao.RoleDao;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.Scope;
import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;
import com.antonioalejandro.smkt.users.pojo.response.ScopeResponse;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.ScopesService;

@RunWith(MockitoJUnitRunner.class)
class ScopeServiceTest {

	@InjectMocks
	private ScopesService SCOPE_SERVICE;

	@Mock
	private RoleService service;

	@Mock
	private RoleDao dao;

	private final Long MOCK_ID = 0L;

	private final Set<Scope> MOCK_SCOPES = createScopeSet();

	private final RoleResponse MOCK_ROLE_RESPONSE_NULL = new RoleResponse(HttpStatus.BAD_REQUEST, "BAD REQUEST");
	private final RoleResponse MOCK_ROLE_RESPONSE_OK = new RoleResponse(new Role(1L, "ADMIN", MOCK_SCOPES));
	private final RoleResponse MOCK_ROLE_RESPONSE_OK_EMPTY = new RoleResponse(new Role(1L, "ADMIN", new HashSet<>()));

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testNullRole() throws Exception {
		when(service.getRoleById(MOCK_ID)).thenReturn(MOCK_ROLE_RESPONSE_NULL);
		ScopeResponse response = SCOPE_SERVICE.getScopesForRole(MOCK_ID);

		assertThat(response).isInstanceOf(ScopeResponse.class);
		assertEquals(MOCK_ROLE_RESPONSE_NULL.getHttpStatus(), response.getHttpStatus());
		assertEquals(MOCK_ROLE_RESPONSE_NULL.getMessage(), response.getMessage());
		assertNull(response.getScopes());
	}

	@Test
	void testNoContent() throws Exception {
		when(service.getRoleById(MOCK_ID)).thenReturn(MOCK_ROLE_RESPONSE_OK_EMPTY);

		ScopeResponse response = SCOPE_SERVICE.getScopesForRole(MOCK_ID);

		assertThat(response).isInstanceOf(ScopeResponse.class);
		assertEquals(HttpStatus.NO_CONTENT, response.getHttpStatus());
		assertEquals("No Content", response.getMessage());
		assertNull(response.getScopes());

	}

	@Test
	void testOk() throws Exception {
		when(service.getRoleById(MOCK_ID)).thenReturn(MOCK_ROLE_RESPONSE_OK);

		ScopeResponse response = SCOPE_SERVICE.getScopesForRole(MOCK_ID);

		assertThat(response).isInstanceOf(ScopeResponse.class);
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertNotNull(response.getScopes());

		List<String> scopes = response.getScopes();

		assertFalse(scopes.isEmpty());
		assertThat(scopes).isInstanceOf(List.class);
		assertThat(scopes.get(0)).isInstanceOf(String.class);

	}

	private Set<Scope> createScopeSet() {
		List<Scope> scopeList = new ArrayList<Scope>();

		scopeList.add(new Scope(2L, "ADMIN"));

		return Set.copyOf(scopeList);

	}
}
