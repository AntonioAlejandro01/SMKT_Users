package com.antonioalejandro.smkt.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antonioalejandro.smkt.users.config.AppEnviroment;
import com.antonioalejandro.smkt.users.config.PersistentContext;
import com.antonioalejandro.smkt.users.service.impl.RoleServiceImpl;
import com.antonioalejandro.smkt.users.service.impl.ScopesServiceImpl;
import com.antonioalejandro.smkt.users.service.impl.UserServiceImpl;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

class PersistentContextTest {

	@Test
	void test() throws Exception {
		PersistentContext context = new PersistentContext();
		assertThat(context.getEncoder()).isInstanceOf(BCryptPasswordEncoder.class);
		assertThat(context.getRoleService()).isInstanceOf(RoleServiceImpl.class);
		assertThat(context.getScopesService()).isInstanceOf(ScopesServiceImpl.class);
		assertThat(context.getUserService()).isInstanceOf(UserServiceImpl.class);
		assertThat(context.getTokenUtils()).isInstanceOf(TokenUtils.class);
		assertThat(context.getAppEnviroment()).isInstanceOf(AppEnviroment.class);
	}
}
