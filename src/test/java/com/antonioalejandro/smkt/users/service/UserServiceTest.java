package com.antonioalejandro.smkt.users.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
import com.antonioalejandro.smkt.users.config.AppEnviroment;
import com.antonioalejandro.smkt.users.dao.UserDao;
import com.antonioalejandro.smkt.users.model.TokenData;
import com.antonioalejandro.smkt.users.model.entity.Role;
import com.antonioalejandro.smkt.users.model.entity.User;
import com.antonioalejandro.smkt.users.model.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.model.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.model.response.RoleResponse;
import com.antonioalejandro.smkt.users.model.response.UserResponse;
import com.antonioalejandro.smkt.users.service.impl.RoleServiceImpl;
import com.antonioalejandro.smkt.users.service.impl.UserServiceImpl;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

@RunWith(MockitoJUnitRunner.class)
class UserServiceTest {

	@InjectMocks
	private UserServiceImpl userService;

	@Mock
	private UserDao userDao;

	@Mock
	private TokenUtils tokenUtils;

	@Mock
	private RoleServiceImpl roleService;

	@Mock
	private AppEnviroment env;

	@Mock
	private BCryptPasswordEncoder encoder;

	@Mock
	private TokenData tokenData;

	private final String MOCK_USERNAME_EMAIL = "admin";
	private final Long MOCK_ID = 1L;
	private final String MOCK_ROLE_NAME = "USER";
	private final String MOCK_SCOPE_ADM = "adm";
	private final String MOCK_SCOPE_SUPER = "super";
	private final String MOCK_SCOPE_READ = "read";
	private final String MOCK_SCOPE_UPDATE = "update";

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		when(env.getScopeReadMin()).thenReturn(MOCK_SCOPE_READ);
		when(env.getScopeAdm()).thenReturn(MOCK_SCOPE_ADM);
		when(env.getScopeSuper()).thenReturn(MOCK_SCOPE_SUPER);
		when(env.getScopeUpdateSelf()).thenReturn(MOCK_SCOPE_UPDATE);
	}

	/* GET_USERS */
	@Test
	void testGetUsersOkRead() throws Exception {
		when(userDao.findAll()).thenReturn(createIterableUsers(false));

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeReadMin()), tokenData)).thenReturn(true);
		UserResponse response = userService.getUsers(tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNotNull(response.getUsers());
		assertNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUsers()).isInstanceOf(List.class);
	}

	@Test
	void testGetUsersOkAdm() throws Exception {
		when(userDao.findAll()).thenReturn(createIterableUsers(false));

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm()), tokenData)).thenReturn(true);
		UserResponse response = userService.getUsers(tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNotNull(response.getUsers());
		assertNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUsers()).isInstanceOf(List.class);
	}

	@Test
	void testGetUsersOkSuper() throws Exception {
		when(userDao.findAll()).thenReturn(createIterableUsers(false));

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		UserResponse response = userService.getUsers(tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNotNull(response.getUsers());
		assertNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUsers()).isInstanceOf(List.class);
	}

	@Test
	void testGetUsersFailScope() throws Exception {
		when(userDao.findAll()).thenReturn(createIterableUsers(false));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(false);
		UserResponse response = userService.getUsers(tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.UNAUTHORIZED, response.getHttpStatus());
		assertNotNull(response.getMessage());

	}

	@Test
	void testGetUsersFail() throws Exception {
		when(userDao.findAll()).thenReturn(createIterableUsers(true));

		UserResponse response = userService.getUsers(tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NO_CONTENT, response.getHttpStatus());
		assertNotNull(response.getMessage());

	}

	/* GET_USER_By_Email_Or_Username */
	@Test
	void testGetUserByEmailOkScopeRead() throws Exception {
		when(userDao.findByEmail(MOCK_USERNAME_EMAIL)).thenReturn(new User());

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeReadMin()), tokenData)).thenReturn(true);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, true, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testGetUserByEmailOkScopeAdm() throws Exception {
		User user = new User();
		Role role = new Role(MOCK_ID, "ADMIN", Set.of());
		user.setRole(role);
		when(userDao.findByEmail(MOCK_USERNAME_EMAIL)).thenReturn(user);

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm()), tokenData)).thenReturn(true);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, true, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testGetUserByEmailOkScopeSuper() throws Exception {
		when(userDao.findByEmail(MOCK_USERNAME_EMAIL)).thenReturn(new User());

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, true, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testGetUserByEmailOkScopeSuperFail() throws Exception {
		User user = new User();
		Role role = new Role(MOCK_ID, "ADMIN", Set.of());
		user.setRole(role);
		when(userDao.findByEmail(MOCK_USERNAME_EMAIL)).thenReturn(user);

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(false);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, true, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.UNAUTHORIZED, response.getHttpStatus());
		assertNotNull(response.getMessage());

	}

	@Test
	void testGetUserByEmailFail() throws Exception {
		when(userDao.findByEmail(MOCK_USERNAME_EMAIL)).thenReturn(null);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, true, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());
	}

	@Test
	void testGetUserByUsernameOk() throws Exception {
		when(userDao.findByUsername(MOCK_USERNAME_EMAIL)).thenReturn(new User());
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, false, tokenData);

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

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, false, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());
	}

	/* GET_BY_USERNAME_KEY */

	@Test
	void testGetUserByUsernameKeyOk() throws Exception {
		when(userDao.findByUsername(MOCK_USERNAME_EMAIL)).thenReturn(new User());
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		UserResponse response = userService.getUserByUsernameKey(MOCK_USERNAME_EMAIL);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testGetUserByUsernameKeyFail() throws Exception {
		when(userDao.findByUsername(MOCK_USERNAME_EMAIL)).thenReturn(null);

		UserResponse response = userService.getUserByEmailOrUsername(MOCK_USERNAME_EMAIL, false, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());

	}

	/* GET_USER_BY_ID */
	@Test
	void testGetUserByIdOk() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(new User()));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		UserResponse response = userService.getUserById(MOCK_ID, tokenData);

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

		UserResponse response = userService.getUserById(MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.NOT_FOUND, response.getHttpStatus());
		assertNotNull(response.getMessage());

	}

	/* UPDATE_USER */
	@Test
	void testUpdateUserOkScopeUpdate() throws Exception {
		User user = createMockUser();
		user.setRole(new Role(MOCK_ID, MOCK_ROLE_NAME, Set.of()));
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeUpdateSelf()), tokenData)).thenReturn(true);
		when(tokenData.getUsername()).thenReturn(user.getUsername());

		when(roleService.getRoleByName(MOCK_ROLE_NAME)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.save(user)).thenReturn(user);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);
	}

	@Test
	void testUpdateUserOkScopeAdm() throws Exception {
		User user = createMockUser();
		user.setRole(new Role(MOCK_ID, "ADMIN", Set.of()));
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm()), tokenData)).thenReturn(true);
		when(tokenData.getUsername()).thenReturn(user.getUsername());

		when(roleService.getRoleByName(MOCK_ROLE_NAME)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.save(user)).thenReturn(user);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);
	}

	@Test
	void testUpdateUserOkScopeAdmOther() throws Exception {
		User user = createMockUser();
		user.setRole(new Role(MOCK_ID, "USER", Set.of()));
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm()), tokenData)).thenReturn(true);
		when(tokenData.getUsername()).thenReturn(user.getUsername() + "X");

		when(roleService.getRoleByName(MOCK_ROLE_NAME)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.save(user)).thenReturn(user);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);
	}

	@Test
	void testUpdateUserOkScopeSuper() throws Exception {
		User user = createMockUser();
		user.setRole(new Role(MOCK_ID, "SUPERADMIN", Set.of()));
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		when(tokenData.getUsername()).thenReturn(user.getUsername());

		when(roleService.getRoleByName(MOCK_ROLE_NAME)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.save(user)).thenReturn(user);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);
	}

	@Test
	void testUpdateUserOkScopeSuperOther() throws Exception {
		User user = createMockUser();
		user.setRole(new Role(MOCK_ID, "ADMIN", Set.of()));
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		when(tokenData.getUsername()).thenReturn(user.getUsername() + "X");

		when(roleService.getRoleByName(MOCK_ROLE_NAME)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.getUsersSameUsername(UtilsForTesting.DATAOK)).thenReturn(0L);

		when(userDao.save(user)).thenReturn(user);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNotNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());
		assertThat(response.getUser()).isInstanceOf(User.class);
	}

	@Test
	void testUpdateUserOkScopeSuperFail() throws Exception {
		User user = createMockUser();
		user.setRole(new Role(MOCK_ID, "ADMIN", Set.of()));
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(user));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(false);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.UNAUTHORIZED, response.getHttpStatus());
		assertNotNull(response.getMessage());
	}

	@Test
	void testUpdateUserFailId() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.empty());

		UserUpdateRequest request = createUserUpdateResquest();

		UserResponse response = userService.updateUser(request, MOCK_ID, tokenData);

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
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);

		when(userDao.getUsersSameEmail(UtilsForTesting.DATAOK)).thenReturn(1L);

		UserResponse response = userService.updateUser(createUserUpdateResquest(), MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
	}

	/* DELETE_USER */
	@Test
	void testDeleteFailId() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.empty());
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		UserResponse response = userService.deleteUser(MOCK_ID, tokenData);

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

		UserResponse response = userService.deleteUser(0L, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());
		assertEquals("Super Admin user can't be deleted. ", response.getMessage());

	}

	@Test
	void testDeleteOkScopeAdm() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(createMockUser()));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm()), tokenData)).thenReturn(true);
		UserResponse response = userService.deleteUser(MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.ACCEPTED, response.getHttpStatus());
		assertNotNull(response.getMessage());
	}

	@Test
	void testDeleteOkScopeSuper() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(createMockUser()));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(true);
		UserResponse response = userService.deleteUser(MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.ACCEPTED, response.getHttpStatus());
		assertNotNull(response.getMessage());
	}

	@Test
	void testDeleteOkScopeSuperFail() throws Exception {
		when(userDao.findById(MOCK_ID)).thenReturn(Optional.of(createMockUser()));
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeSuper()), tokenData)).thenReturn(false);
		UserResponse response = userService.deleteUser(MOCK_ID, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.UNAUTHORIZED, response.getHttpStatus());
		assertNotNull(response.getMessage());
	}

	/* CREATE_USER */
	@Test
	void testCreateUserOk() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();
		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm(), env.getScopeSuper()), tokenData))
				.thenReturn(true);
		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(0L);

		when(userDao.getUsersSameUsername(request.getUsername())).thenReturn(0L);

		UserResponse response = userService.createUser(request, tokenData);

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

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm(), env.getScopeSuper()), tokenData))
				.thenReturn(true);
		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));

		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(0L);

		when(userDao.getUsersSameUsername(request.getUsername())).thenReturn(0L);

		UserResponse response = userService.createUser(request, tokenData);

		assertThat(response).isInstanceOf(UserResponse.class);
		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNull(response.getHttpStatus());
		assertNull(response.getMessage());

	}

	@Test
	void testCreateUserFail() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm(), env.getScopeSuper()), tokenData))
				.thenReturn(true);
		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));
		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(1L);

		UserResponse response = userService.createUser(request, tokenData);

		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());

	}

	@Test
	void testCreateUserFailUsername() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm(), env.getScopeSuper()), tokenData))
				.thenReturn(true);
		when(roleService.getRoleById(0L)).thenReturn(new RoleResponse(new Role()));
		when(userDao.getUsersSameEmail(request.getEmail())).thenReturn(0L);
		when(userDao.getUsersSameUsername(request.getUsername())).thenReturn(1L);

		UserResponse response = userService.createUser(request, tokenData);

		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, response.getHttpStatus());
		assertNotNull(response.getMessage());

	}

	@Test
	void testCreateUserFailScope() throws Exception {

		UserRegistrationRequest request = createUserRegistrationRequest();

		when(tokenUtils.isAuthorized(Arrays.asList(env.getScopeAdm(), env.getScopeSuper()), tokenData))
				.thenReturn(false);

		UserResponse response = userService.createUser(request, tokenData);

		assertNull(response.getUsers());
		assertNull(response.getUser());
		assertNotNull(response.getHttpStatus());
		assertEquals(HttpStatus.UNAUTHORIZED, response.getHttpStatus());
		assertNotNull(response.getMessage());

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

		User user;
		Role role;
		if (!isVoid) {
			user = new User();
			role = new Role(MOCK_ID, "ADMIN", Set.of());
			user.setRole(role);
			users.add(user);
			user = new User();
			role = new Role(MOCK_ID, "SUPERADMIN", Set.of());
			user.setRole(role);
			users.add(user);
		}

		return () -> users.iterator();

	}
}
