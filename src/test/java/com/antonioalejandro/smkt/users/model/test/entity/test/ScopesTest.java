package com.antonioalejandro.smkt.users.model.test.entity.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.UtilTest;
import com.antonioalejandro.smkt.users.entity.Scope;

public class ScopesTest {

	@Test
	void test() throws Exception {
		Scope scope = UtilTest.getScope();

		Scope scope2 = UtilTest.getScope();

		assertNotSame(scope, scope2);
		assertEquals(scope.getId(), scope2.getId());
		assertEquals(scope.getName(), scope2.getName());

		scope2.setId(UtilTest.LONGKO);
		scope2.setName(UtilTest.DATAKO);

		assertNotSame(scope, scope2);
		assertNotEquals(scope.getId(), scope2.getId());
		assertNotEquals(scope.getName(), scope2.getName());
	}
}
