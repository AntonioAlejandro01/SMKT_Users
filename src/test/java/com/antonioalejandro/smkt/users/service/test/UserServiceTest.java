package com.antonioalejandro.smkt.users.service.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antonioalejandro.smkt.users.UtilsForTesting;
import com.antonioalejandro.smkt.users.dao.UserDao;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.UserService;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

	@InjectMocks
	private UserService userService;

	@Mock
	private UserDao userDao;

	@Mock
	private RoleService roleService;

	@Mock
	private BCryptPasswordEncoder encoder;

	private final String MOCK_USERNAME_EMAIL = "admin";
	private final Long MOCK_ID = 1L;
	private final String MOCK_ROLE_NAME = "ADMIN";

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
	void testGetUserByIdOk() throws Exception {
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

	@Test
	void testUpdateUserOk() throws Exception {
		User user = createMockUser();

		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.save(user)).thenReturn(user);

		UserUpdateRequest request = createUserUpdateResquest();
		request.setRole(MOCK_ROLE_NAME + "X");
		when(roleService.getRoleByName(request.getRole())).thenReturn(new RoleResponse(new Role()));

		UserResponse response = userService.updateUser(request, MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testUpdateUserOkWithoutChangesOnUsernameAndEmail() throws Exception {
		User user = createMockUser();

		user.setEmail(UtilsForTesting.DATAOK);
		user.setUsername(UtilsForTesting.DATAOK);

		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.save(user)).thenReturn(user);

		UserUpdateRequest request = createUserUpdateResquest();

		when(roleService.getRoleByName(request.getRole())).thenReturn(new RoleResponse(new Role()));

		UserResponse response = userService.updateUser(request, MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testUpdateUserFailId() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.empty());

		UserUpdateRequest request = createUserUpdateResquest();

		UserResponse response = userService.updateUser(request, MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Id don't exists. ", response.getMessage());

	}

	@Test
	void testUpdateUserFailEmail() throws Exception {

		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(createMockUser()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(1L);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Email , Username already exists or Role Name is not valid. ", response.getMessage());
	}

	@Test
	void testUpdateUserFailUsername() throws Exception {

		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(createMockUser()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(1L);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Email , Username already exists or Role Name is not valid. ", response.getMessage());
	}

	@Test
	void testUpdateUserFailRole() throws Exception {

		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(createMockUser()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		UserUpdateRequest request = createUserUpdateResquest();

		request.setRole(MOCK_ROLE_NAME + "X");

		when(roleService.getRoleByName(request.getRole())).thenReturn(new RoleResponse(HttpStatus.NOT_FOUND, null));

		UserResponse response = userService.updateUser(request, MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Email , Username already exists or Role Name is not valid. ", response.getMessage());
	}

	@Test
	void testDeleteOk() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(createMockUser()));

		UserResponse response = userService.deleteUser(MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.ACCEPTED, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("User was deleted", response.getMessage());

	}

	@Test
	void testDeleteFailId() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.empty());

		UserResponse response = userService.deleteUser(MOCK_ID);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("User doesn't exists", response.getMessage());

	}

	@Test
	void testDeleteFailSuper() throws Exception {

		UserResponse response = userService.deleteUser(0L);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Super Admin user can't be deleted. ", response.getMessage());

	}

	@Test
	void testCreateUserOk() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();

		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(0L);

		when(userDao.getUsersSameUsername(request.getUsername())).thenReturn(0L);

		UserResponse response = userService.createUser(request);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());

	}

	@Test
	void testCreateUserOkWithoutLastname() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();

		request.setLastname(null);

		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(0L);

		when(userDao.getUsersSameUsername(request.getUsername())).thenReturn(0L);

		UserResponse response = userService.createUser(request);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());

	}

	@Test
	void testCreateUserFailEmail() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();

		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(1L);

		UserResponse response = userService.createUser(request);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("BAD REQUEST", response.getMessage());

	}

	@Test
	void testCreateUserFailUsername() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();

		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(0L);

		when(userDao.getUsersSameUsername(request.getUsername())).thenReturn(1L);

		UserResponse response = userService.createUser(request);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("BAD REQUEST", response.getMessage());

	}

	private UserUpdateRequest createUserUpdateResquest() {
		UserUpdateRequest request = new UserUpdateRequest();

		request.setEmail(UtilsForTesting.DATAOK);
		request.setUsername(UtilsForTesting.DATAOK);
		request.setLastname(UtilsForTesting.DATAOK);
		request.setName(UtilsForTesting.DATAOK);
		request.setPassword(UtilsForTesting.DATAOK);
		request.setRole(MOCK_ROLE_NAME);

		return request;

	}

	private UserRegistrationRequest createUserRegistrationRequest() {
		UserRegistrationRequest request = new UserRegistrationRequest();

		request.setEmail(UtilsForTesting.DATAOK);
		request.setLastname(UtilsForTesting.DATAOK);
		request.setName(UtilsForTesting.DATAOK);
		request.setPassword(UtilsForTesting.DATAOK);
		request.setUsername(UtilsForTesting.DATAOK);

		return request;
	}

	private User createMockUser() {
		User user = new User();

		user.setEmail(UtilsForTesting.DATAKO);
		user.setUsername(UtilsForTesting.DATAKO);
		user.setLastname(UtilsForTesting.DATAOK);
		user.setName(UtilsForTesting.DATAOK);
		user.setPassword(UtilsForTesting.DATAOK);

		user.setId(MOCK_ID);

		user.setRole(new Role(MOCK_ID, MOCK_ROLE_NAME, null));

		return user;
	}

	private Iterable<User> createIterableUsers(boolean isVoid) {
		List<User> users = new ArrayList<>();

		if (!isVoid) {
			users.add(new User());
		}

		return () -> users.iterator();

	}
}
