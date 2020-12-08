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
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;
import com.antonioalejandro.smkt.users.service.UserService;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

import antlr.collections.List;

class UserControllerTest {

	@InjectMocks
	private UserController controller;

	@Mock
	private UserService Userservice;

	@Mock
	private TokenUtils utils;

	private final String TOKEN = "t.o.k.e.n";

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testGetUsers() throws Exception {
		when(utils.isAuthorized(Arrays.asList(null, null, null))).thenReturn(true);

		when(Userservice.getUsers()).thenReturn(new UserResponse(new ArrayList<User>()));

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

}
