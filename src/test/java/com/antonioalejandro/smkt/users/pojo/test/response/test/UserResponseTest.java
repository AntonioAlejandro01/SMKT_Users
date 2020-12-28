package com.antonioalejandro.smkt.users.pojo.test.response.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;

class UserResponseTest {

	@Test
	void test() throws Exception {
		UserResponse response = new UserResponse(null, null, new ArrayList<>());

		assertNull(response.getUser());
		assertThat(response.getUsers()).isInstanceOf(ArrayList.class);

		response = new UserResponse(null, null, new User());

		assertNull(response.getUsers());
		assertThat(response.getUser()).isInstanceOf(User.class);

		response = new UserResponse(null, null);

		assertNull(response.getUsers());
		assertNull(response.getUser());

		response = new UserResponse(new ArrayList<>());

		assertNull(response.getUser());
		assertThat(response.getUsers()).isInstanceOf(ArrayList.class);

		response = new UserResponse(new User());

		assertNull(response.getUsers());
		assertThat(response.getUser()).isInstanceOf(User.class);
	}
}
