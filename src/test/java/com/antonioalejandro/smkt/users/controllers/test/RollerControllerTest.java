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

import com.antonioalejandro.smkt.users.controllers.RoleController;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;
import com.antonioalejandro.smkt.users.service.RoleService;

class RollerControllerTest {

	@InjectMocks
	private RoleController roleController;

	@Mock
	private RoleService service;

	private final String MOCK_NAME = "ADMIN";

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetRolesOk() throws Exception {
		when(service.getRoles()).thenReturn(new RoleResponse(createRoleList()));

		ResponseEntity<RoleResponse> response = roleController.getRoles();

		assertThat(response).isInstanceOf(ResponseEntity.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		RoleResponse roleResponse = response.getBody();

		assertThat(roleResponse).isInstanceOf(RoleResponse.class);

		assertNull(roleResponse.getHttpStatus());
		assertNull(roleResponse.getMessage());

		assertNull(roleResponse.getRole());

		assertNotNull(roleResponse.getRoles());
		assertThat(roleResponse.getRoles()).isInstanceOf(List.class);
		assertFalse(roleResponse.getRoles().isEmpty());

	}

	@Test
	void testGetRolesFail() throws Exception {
		when(service.getRoles()).thenReturn(new RoleResponse(new ArrayList<Role>()));

		ResponseEntity<RoleResponse> response = roleController.getRoles();

		assertThat(response).isInstanceOf(ResponseEntity.class);

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

		RoleResponse roleResponse = response.getBody();

		assertThat(roleResponse).isInstanceOf(RoleResponse.class);

		assertNotNull(roleResponse.getHttpStatus());
		assertEquals(HttpStatus.NO_CONTENT, roleResponse.getHttpStatus());
		assertNotNull(roleResponse.getMessage());
		assertEquals("No Content", roleResponse.getMessage());

		assertNull(roleResponse.getRole());
		assertNull(roleResponse.getRoles());

	}

	@Test
	void testGetRoleByNameOk() throws Exception {
		when(service.getRoleByName(MOCK_NAME)).thenReturn(new RoleResponse(new Role()));

		ResponseEntity<RoleResponse> response = roleController.getRoleByName(MOCK_NAME);

		assertThat(response).isInstanceOf(ResponseEntity.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());

		RoleResponse roleResponse = response.getBody();

		assertThat(roleResponse).isInstanceOf(RoleResponse.class);

		assertNull(roleResponse.getHttpStatus());
		assertNull(roleResponse.getMessage());

		assertNotNull(roleResponse.getRole());
		assertThat(roleResponse.getRole()).isInstanceOf(Role.class);

		assertNull(roleResponse.getRoles());

	}

	@Test
	void testGetRoleByNameFailBadRequest() throws Exception {

		ResponseEntity<RoleResponse> response = roleController.getRoleByName("");

		assertThat(response).isInstanceOf(ResponseEntity.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		RoleResponse roleResponse = response.getBody();

		assertThat(roleResponse).isInstanceOf(RoleResponse.class);

		assertNotNull(roleResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, roleResponse.getHttpStatus());

		assertNotNull(roleResponse.getMessage());
		assertFalse(roleResponse.getMessage().isBlank());

		assertNull(roleResponse.getRole());

		assertNull(roleResponse.getRoles());

	}

	@Test
	void testGetRoleByNameFailBadRequest2() throws Exception {

		ResponseEntity<RoleResponse> response = roleController.getRoleByName(null);

		assertThat(response).isInstanceOf(ResponseEntity.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

		RoleResponse roleResponse = response.getBody();

		assertThat(roleResponse).isInstanceOf(RoleResponse.class);

		assertNotNull(roleResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, roleResponse.getHttpStatus());

		assertNotNull(roleResponse.getMessage());
		assertFalse(roleResponse.getMessage().isBlank());

		assertNull(roleResponse.getRole());

		assertNull(roleResponse.getRoles());

	}

	@Test
	void testGetRoleByNameFail() throws Exception {
		when(service.getRoleByName(MOCK_NAME)).thenReturn(new RoleResponse(HttpStatus.NOT_FOUND, "X"));

		ResponseEntity<RoleResponse> response = roleController.getRoleByName(MOCK_NAME);

		assertThat(response).isInstanceOf(ResponseEntity.class);

		assertNotEquals(HttpStatus.OK, response.getStatusCode());

		RoleResponse roleResponse = response.getBody();

		assertThat(roleResponse).isInstanceOf(RoleResponse.class);

		assertNotNull(roleResponse.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, roleResponse.getHttpStatus());
		assertNotNull(roleResponse.getMessage());

		assertFalse(roleResponse.getMessage().isBlank());

		assertNull(roleResponse.getRole());
		assertNull(roleResponse.getRoles());

	}

	private List<Role> createRoleList() {
		List<Role> roles = new ArrayList<Role>();

		roles.add(new Role());

		return roles;
	}
}
