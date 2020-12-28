package com.antonioalejandro.smkt.users;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.config.AppEnviroment;

class AppEnviromentTest {

	@Test
	void test() throws Exception {
		AppEnviroment env = new AppEnviroment();
		assertNull(env.getAppSecret());
		assertNull(env.getAppUser());
		assertNull(env.getDefaultRoleId());
		assertNull(env.getOauthId());
		assertNull(env.getOauthPath());
		assertNull(env.getScopeAdm());
		assertNull(env.getScopeReadMin());
		assertNull(env.getScopeSuper());
		assertNull(env.getScopeUpdateSelf());
		assertNull(env.getAppKeySecret());
		assertNull(env.getSuperAdminId());
		assertNotNull(env.toString());

	}
}
