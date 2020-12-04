package com.antonioalejandro.smkt.users.model.test.entity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.UtilTest;
import com.antonioalejandro.smkt.users.entity.User;

public class UserTest {

	@Test
	void test() throws Exception {

		User user1 = UtilTest.getUser();

		User user2 = UtilTest.getUser();

		assertNotSame(user1, user2);
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getLastname(), user2.getUsername());
		assertEquals(user1.getEmail(), user2.getEmail());
		assertEquals(user1.getUsername(), user2.getUsername());
		assertEquals(user1.getPassword(), user2.getPassword());
		assertEquals(user1.getRole().getClass().toString(), user2.getRole().getClass().toString());

		UtilTest.modifyUser(user2);

		assertNotSame(user1, user2);
		assertNotEquals(user1.getId(), user2.getId());
		assertNotEquals(user1.getName(), user2.getName());
		assertNotEquals(user1.getLastname(), user2.getUsername());
		assertNotEquals(user1.getEmail(), user2.getEmail());
		assertNotEquals(user1.getUsername(), user2.getUsername());
		assertNotEquals(user1.getPassword(), user2.getPassword());
		assertEquals(user1.getRole().getClass().toString(), user2.getRole().getClass().toString());

	}
}
