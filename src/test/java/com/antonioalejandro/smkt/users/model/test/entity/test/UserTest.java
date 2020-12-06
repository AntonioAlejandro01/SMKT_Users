package com.antonioalejandro.smkt.users.model.test.entity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.UtilsForTesting;
import com.antonioalejandro.smkt.users.entity.User;

class UserTest {

	@Test
	void test() throws Exception {

		User user1 = UtilsForTesting.getUser();

		assertEquals(UtilsForTesting.DATAOK, user1.getRoleName());

		UtilsForTesting.modifyUser(user1);

		assertEquals(UtilsForTesting.DATAKO, user1.getRoleName());

	}
}
