package com.antonioalejandro.smkt.users.model.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.UtilsForTesting;

class UserTest {

	@Test
	void testUser() throws Exception {

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

		assertThat(user1.getRoleName()).isInstanceOf(String.class);

		assertEquals(UtilsForTesting.DATAKO, user1.getRoleName());
		assertEquals(UtilsForTesting.DATAKO, user1.getRoleName());
		assertEquals(UtilsForTesting.DATAKO, user1.getEmail());
		assertEquals(UtilsForTesting.DATAKO, user1.getLastname());
		assertEquals(UtilsForTesting.DATAKO, user1.getName());
		assertEquals(UtilsForTesting.DATAKO, user1.getPassword());
		assertEquals(UtilsForTesting.DATAKO, user1.getUsername());
		assertEquals(UtilsForTesting.LONGKO, user1.getId());
		

		user1.setRole(null);
		assertNull(user1.getRoleName());
	}
}
