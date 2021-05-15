package com.antonioalejandro.smkt.users.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.UtilsForTesting;

 class ScopesTest {

	@Test
	void test() throws Exception {
		Scope scope = UtilsForTesting.getScope();

		Scope scope2 = UtilsForTesting.getScope();

		assertNotSame(scope, scope2);
		assertEquals(scope.getId(), scope2.getId());
		assertEquals(scope.getName(), scope2.getName());

		scope2.setId(UtilsForTesting.LONGKO);
		scope2.setName(UtilsForTesting.DATAKO);

		assertNotSame(scope, scope2);
		assertNotEquals(scope.getId(), scope2.getId());
		assertNotEquals(scope.getName(), scope2.getName());
	}
}
