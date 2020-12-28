package com.antonioalejandro.smkt.users.pojo.test.response.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;

class RoleResponseTest {

	@Test
	void test() throws Exception {
		RoleResponse response = new RoleResponse(null, null, new ArrayList<>());

		assertNull(response.getRole());
		assertThat(response.getRoles()).isInstanceOf(ArrayList.class);

		response = new RoleResponse(null, null, new Role());

		assertNull(response.getRoles());
		assertThat(response.getRole()).isInstanceOf(Role.class);

		response = new RoleResponse(null, null);

		assertNull(response.getRoles());
		assertNull(response.getRole());

		response = new RoleResponse(new ArrayList<>());

		assertNull(response.getRole());
		assertThat(response.getRoles()).isInstanceOf(ArrayList.class);

		response = new RoleResponse(new Role());

		assertNull(response.getRoles());
		assertThat(response.getRole()).isInstanceOf(Role.class);
	}

}
