package com.antonioalejandro.smkt.users.model.test.entity.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.UtilsForTesting;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.User;

class UserTest {

	@Test
	void test() throws Exception {

		User user1 = UtilsForTesting.getUser();

		assertEquals(UtilsForTesting.DATAOK, user1.getRoleName());
		assertEquals(UtilsForTesting.DATAOK, user1.getEmail());
		assertEquals(UtilsForTesting.DATAOK, user1.getLastname());
		assertEquals(UtilsForTesting.DATAOK, user1.getName());
		assertEquals(UtilsForTesting.DATAOK, user1.getPassword());
		assertEquals(UtilsForTesting.DATAOK, user1.getUsername());
		assertEquals(UtilsForTesting.LONGOK, user1.getId());
		assertThat(user1.getRole()).isInstanceOf(Role.class);

		UtilsForTesting.modifyUser(user1);

		assertEquals(UtilsForTesting.DATAKO, user1.getRoleName());
		assertEquals(UtilsForTesting.DATAKO, user1.getRoleName());
		assertEquals(UtilsForTesting.DATAKO, user1.getEmail());
		assertEquals(UtilsForTesting.DATAKO, user1.getLastname());
		assertEquals(UtilsForTesting.DATAKO, user1.getName());
		assertEquals(UtilsForTesting.DATAKO, user1.getPassword());
		assertEquals(UtilsForTesting.DATAKO, user1.getUsername());
		assertEquals(UtilsForTesting.LONGKO, user1.getId());

		user1 = new User(1L, UtilsForTesting.DATAOK, UtilsForTesting.DATAOK, UtilsForTesting.DATAOK,
				UtilsForTesting.DATAOK, UtilsForTesting.DATAOK, new Role(1L,UtilsForTesting.DATAOK,null));

		assertEquals(UtilsForTesting.DATAOK, user1.getRoleName());
		assertEquals(UtilsForTesting.DATAOK, user1.getEmail());
		assertEquals(UtilsForTesting.DATAOK, user1.getLastname());
		assertEquals(UtilsForTesting.DATAOK, user1.getName());
		assertEquals(UtilsForTesting.DATAOK, user1.getPassword());
		assertEquals(UtilsForTesting.DATAOK, user1.getUsername());
		assertEquals(UtilsForTesting.LONGOK, user1.getId());
		assertThat(user1.getRole()).isInstanceOf(Role.class);
		

		assertThat(user1.getRoleName()).isInstanceOf(String.class);
		user1.setRole(null);
		assertNull(user1.getRoleName());
	}
}
