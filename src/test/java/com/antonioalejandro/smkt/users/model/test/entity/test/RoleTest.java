package com.antonioalejandro.smkt.users.model.test.entity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.UtilsForTesting;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.Scope;

 class RoleTest {

	@Test
	void test() throws Exception {

		Role role1 = UtilsForTesting.getRole();

		Role role2 = UtilsForTesting.getRole();

		assertNotSame(role1, role2);
		assertEquals(role1.getId(), role2.getId());
		assertEquals(role1.getName(), role2.getName());
		assertEquals(role1.getScopes().getClass().toString(), role2.getScopes().getClass().toString());

		role2.setName(UtilsForTesting.DATAKO);
		role2.setId(UtilsForTesting.LONGKO);

		HashSet<Scope> scopes = new HashSet<>();

		role1.setScopes(scopes);
		role2.setScopes(scopes);
		

		assertNotSame(role1, role2);
		assertNotEquals(role1.getId(), role2.getId());
		assertNotEquals(role1.getName(), role2.getName());
		assertSame(role1.getScopes(), role2.getScopes());
	}
}
