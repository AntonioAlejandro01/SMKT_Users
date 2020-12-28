package com.antonioalejandro.smkt.users.controllers.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.antonioalejandro.smkt.users.controllers.UserController;
import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.TokenData;
import com.antonioalejandro.smkt.users.pojo.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;
import com.antonioalejandro.smkt.users.service.UserService;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

class UserControllerTest {

	@InjectMocks
	private UserController controller;

	@Mock
	private UserService Userservice;

	@Mock
	private TokenUtils utils;

	private final String TOKEN = "t.o.k.e.n";

	private final UserRegistrationRequest CREATE_REQ = new UserRegistrationRequest();
	private TokenData tokenData = new TokenData();

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetUsers() throws Exception {
		when(utils.isAuthorized(Arrays.asList(null, null, null), new TokenData())).thenReturn(true);
		when(utils.getDataToken(TOKEN)).thenReturn(tokenData);
		when(Userservice.getUsers(tokenData)).thenReturn(new UserResponse(new ArrayList<User>()));

		ResponseEntity<UserResponse> response = controller.getUsers(TOKEN);

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNull(userResponse.getUser());
		assertNull(userResponse.getHttpStatus());
		assertNull(userResponse.getMessage());
		assertNotNull(userResponse.getUsers());
		assertThat(userResponse.getUsers()).isInstanceOf(ArrayList.class);

	}

	
	void verifyBadRequest(ResponseEntity<UserResponse> response) {
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, userResponse.getHttpStatus());
		assertNotNull(userResponse.getMessage());
		assertNull(userResponse.getUser());
		assertNull(userResponse.getUsers());

	}

	@Test
	void testSearchUsersBadRequest() throws Exception {

		verifyBadRequest(controller.searchUser(TOKEN, null, null, null));
		verifyBadRequest(controller.searchUser(TOKEN, null, "", null));
		verifyBadRequest(controller.searchUser(TOKEN, null, "", ""));
		verifyBadRequest(controller.searchUser(TOKEN, null, "id", "4s"));
		verifyBadRequest(controller.searchUser(TOKEN, null, "username", "4sas"));
		verifyBadRequest(controller.searchUser(TOKEN, null, "username", ""));
		verifyBadRequest(controller.searchUser(TOKEN, null, "email", ""));
		verifyBadRequest(controller.searchUser(TOKEN, null, "email", "asdasd"));

	}

	@Test
	void testSearchUsersOkId() throws Exception {
		when(Userservice.getUserById(1, tokenData)).thenReturn(new UserResponse(new User()));
		when(utils.getDataToken(TOKEN)).thenReturn(tokenData);
		ResponseEntity<UserResponse> response = controller.searchUser(TOKEN, null, "id", "1");

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getUser());
		assertNull(userResponse.getHttpStatus());
		assertNull(userResponse.getMessage());
		assertNull(userResponse.getUsers());
		assertThat(userResponse.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testSearchUsersOkUsernameOrEmail() throws Exception {
		when(Userservice.getUserByEmailOrUsername("qwerty@gmail.com", true, tokenData))
				.thenReturn(new UserResponse(new User()));
		when(utils.getDataToken(TOKEN)).thenReturn(tokenData);

		ResponseEntity<UserResponse> response = controller.searchUser(TOKEN, null, "email", "qwerty@gmail.com");

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getUser());
		assertNull(userResponse.getHttpStatus());
		assertNull(userResponse.getMessage());
		assertNull(userResponse.getUsers());
		assertThat(userResponse.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testCreateUser() throws Exception {
		CREATE_REQ.setEmail("test@test.com");
		CREATE_REQ.setName("Testing");
		CREATE_REQ.setUsername("TESTING");
		CREATE_REQ.setPassword("Testing@20");
		CREATE_REQ.setLastname("LASTNAME");

		when(Userservice.createUser(CREATE_REQ, tokenData)).thenReturn(new UserResponse(new User()));
		when(utils.getDataToken(TOKEN)).thenReturn(tokenData);

		ResponseEntity<UserResponse> response = controller.create(TOKEN, CREATE_REQ);

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getUser());
		assertNull(userResponse.getHttpStatus());
		assertNull(userResponse.getMessage());
		assertNull(userResponse.getUsers());
		assertThat(userResponse.getUser()).isInstanceOf(User.class);

	}

	@Test
	void testCreateUserBadRequest() throws Exception {
		CREATE_REQ.setEmail("test@test.com");
		CREATE_REQ.setName("Testing");
		CREATE_REQ.setUsername("TESTING");
		CREATE_REQ.setPassword("Te@20"); // bad password
		ResponseEntity<UserResponse> response = controller.searchUser(TOKEN, null, "email", "asdasd");

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, userResponse.getHttpStatus());
		assertNotNull(userResponse.getMessage());
		assertNull(userResponse.getUser());
		assertNull(userResponse.getUsers());

	}

	@Test
	void testCreateUserBadRequest1() throws Exception {
		CREATE_REQ.setEmail("test@test.com");
		CREATE_REQ.setName("Tg"); // bad username
		CREATE_REQ.setUsername("TESTING");
		CREATE_REQ.setPassword("Testing@20");
		ResponseEntity<UserResponse> response = controller.searchUser(TOKEN, null, "email", "asdasd");

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, userResponse.getHttpStatus());
		assertNotNull(userResponse.getMessage());
		assertNull(userResponse.getUser());
		assertNull(userResponse.getUsers());

	}

	@Test
	void testCreateUserBadRequest2() throws Exception {
		CREATE_REQ.setEmail("test@test.com");
		CREATE_REQ.setName("Testing");
		CREATE_REQ.setUsername("TESTING");
		CREATE_REQ.setPassword("Te@20");
		CREATE_REQ.setLastname("LA"); // bad lastname
		ResponseEntity<UserResponse> response = controller.searchUser(TOKEN, null, "email", "asdasd");

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, userResponse.getHttpStatus());
		assertNotNull(userResponse.getMessage());
		assertNull(userResponse.getUser());
		assertNull(userResponse.getUsers());

	}

	@Test
	void testDeleteUser() throws Exception {
		Long id = 1L;
		when(Userservice.deleteUser(id, tokenData)).thenReturn(new UserResponse(HttpStatus.ACCEPTED, ""));
		when(utils.getDataToken(TOKEN)).thenReturn(tokenData);

		ResponseEntity<UserResponse> response = controller.deleteUser(TOKEN, id);

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getHttpStatus());
		assertEquals(HttpStatus.ACCEPTED, userResponse.getHttpStatus());
		assertNotNull(userResponse.getMessage());
		assertNull(userResponse.getUser());
		assertNull(userResponse.getUsers());
	}

	@Test
	void testDeleteUserBadRequest() throws Exception {
		Long id = 0L;

		ResponseEntity<UserResponse> response = controller.deleteUser(TOKEN, id);

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, userResponse.getHttpStatus());
		assertNotNull(userResponse.getMessage());
		assertNull(userResponse.getUser());
		assertNull(userResponse.getUsers());
	}

	@Test
	void testUpdateUserBadRequest() throws Exception {
		Long id = 0L;

		ResponseEntity<UserResponse> response = controller.putUserById(TOKEN, null, id);

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getHttpStatus());
		assertEquals(HttpStatus.BAD_REQUEST, userResponse.getHttpStatus());
		assertNotNull(userResponse.getMessage());
		assertNull(userResponse.getUser());
		assertNull(userResponse.getUsers());
	}

	@Test
	void testPutUser() throws Exception {
		Long id = 1L;
		when(Userservice.updateUser(null, id, tokenData)).thenReturn(new UserResponse(new User()));
		when(utils.getDataToken(TOKEN)).thenReturn(tokenData);

		ResponseEntity<UserResponse> response = controller.putUserById(TOKEN, null, id);

		assertThat(response).isInstanceOf(ResponseEntity.class);
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
		assertNotNull(response.getBody());
		assertThat(response.getBody()).isInstanceOf(UserResponse.class);
		UserResponse userResponse = response.getBody();
		assertNotNull(userResponse.getUser());
		assertNull(userResponse.getHttpStatus());
		assertNull(userResponse.getMessage());
		assertNull(userResponse.getUsers());
		assertThat(userResponse.getUser()).isInstanceOf(User.class);

	}

}
