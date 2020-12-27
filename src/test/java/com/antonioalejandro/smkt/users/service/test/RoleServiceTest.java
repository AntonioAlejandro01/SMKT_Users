package com.antonioalejandro.smkt.users.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

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
import com.antonioalejandro.smkt.users.service.RoleService;

@RunWith(MockitoJUnitRunner.class)
class RoleServiceTest {

	@InjectMocks
	private RoleService roleService;

	@Mock
	private RoleDao roleDao;

	private final Long Mock_ID = 1L;
	private final String MOCK_NAME = "ADMIN";

	private final Role role = new Role(Mock_ID, MOCK_NAME, new HashSet<Scope>());

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetRoles() throws Exception {
		when(roleDao.findAll()).thenReturn(createIterableRole());

		RoleResponse response = roleService.getRoles();

		assertThat(response).isInstanceOf(RoleResponse.class);
		assertNotNull(response.getRoles());
		assertNull(response.getRole());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getRoles()).isInstanceOf(List.class);

	}

	@Test
	void testGetRoleByIdOk() throws Exception {
		when(roleDao.findById(Mock_ID)).thenReturn(Optional.of(role));

		RoleResponse response = roleService.getRoleById(Mock_ID);

		assertThat(response).isInstanceOf(RoleResponse.class);
		assertNull(response.getRoles());
		assertNotNull(response.getRole());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getRole()).isInstanceOf(Role.class);

	}

	@Test
	void testGetRolesByIdFail() throws Exception {
		when(roleDao.findById(Mock_ID)).thenReturn(Optional.empty());

		RoleResponse response = roleService.getRoleById(Mock_ID);

		assertThat(response).isInstanceOf(RoleResponse.class);
		assertNull(response.getRoles());
		assertNull(response.getRole());
		assertNotNull(response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertEquals("Not Found", response.getMessage());
	}

	@Test
	void testGetRoleByNameOk() throws Exception {
		when(roleDao.findByName(MOCK_NAME)).thenReturn(role);

		RoleResponse response = roleService.getRoleByName(MOCK_NAME);

		assertThat(response).isInstanceOf(RoleResponse.class);
		assertNull(response.getRoles());
		assertNotNull(response.getRole());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getRole()).isInstanceOf(Role.class);

	}

	@Test
	void testGetRoleByNameFail() throws Exception {
		when(roleDao.findByName(MOCK_NAME)).thenReturn(null);

		RoleResponse response = roleService.getRoleByName(MOCK_NAME);

		assertThat(response).isInstanceOf(RoleResponse.class);
		assertNull(response.getRoles());
		assertNull(response.getRole());
		assertNotNull(response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
	}

	private Iterable<Role> createIterableRole() {
		List<Role> roles = new ArrayList<>();
		roles.add(role);

		return () -> roles.iterator();

	}

}
