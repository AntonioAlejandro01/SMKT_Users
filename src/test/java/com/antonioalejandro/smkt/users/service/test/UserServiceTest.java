package com.antonioalejandro.smkt.users.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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

import com.antonioalejandro.smkt.users.dao.UserDao;
import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserDao userDao;

	@Mock
	private RoleService roleService;

	private final String MOCK_USERNAME_EMAIL = "admin";
	private final Long MOCK_ID = 0L;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetUsersOk() throws Exception {
		when(userDao.findAll()).thenReturn(createIterableUsers(false));

		UserResponse response = userService.getUsers();

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNotNull(response.getUsers());
		assertNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUsers()).isInstanceOf(List.class);

	}

	@Test
	void testGetUsersFail() throws Exception {
		when(userDao.findAll()).thenReturn(createIterableUsers(true));

		UserResponse response = userService.getUsers();

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NO_CONTENT, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("No Content", response.getMessage());

	}

	@Test
	void testGetUserByEmailOk() throws Exception {
		when(userDao.findByEmail(MOCK_USERNAME_EMAIL)).thenReturn(new User());

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, true);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testGetUserByEmailFail() throws Exception {
		when(userDao.findByEmail(MOCK_USERNAME_EMAIL)).thenReturn(null);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, true);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Email does't exists", response.getMessage());
	}

	@Test
	void testGetUserByUsernameOk() throws Exception {
		when(userDao.findByUsername(MOCK_USERNAME_EMAIL)).thenReturn(new User());

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, false);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testGetUserByUsernameFail() throws Exception {
		when(userDao.findByUsername(MOCK_USERNAME_EMAIL)).thenReturn(null);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, false);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Username does't exists", response.getMessage());
	}
	
	@Test
	void testGetUSerByIdOk() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(new User()));
		
		UserResponse response = userService.getUserById(MOCK_ID);
		
		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);
		
	}
	
	@Test
	void testGetUserByIdFail() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.empty());

		UserResponse response = userService.getUserById(MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Id not found. ", response.getMessage());
	}

	private Iterable<User> createIterableUsers(boolean isVoid) {
		List<User> users = new ArrayList<>();

		if (!isVoid) {
			users.add(new User());
		}

		return () -> users.iterator();

	}
}
