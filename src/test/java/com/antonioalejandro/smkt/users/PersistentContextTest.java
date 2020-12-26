package com.antonioalejandro.smkt.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antonioalejandro.smkt.users.config.PersistentContext;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.ScopesService;
import com.antonioalejandro.smkt.users.service.UserService;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

class PersistentContextTest {

	@Test
	void test() throws Exception {
		PersistentContext context = new PersistentContext();
		assertThat(context.getEncoder()).isInstanceOf(BCryptPasswordEncoder.class);
		assertThat(context.getRoleService()).isInstanceOf(RoleService.class);
		assertThat(context.getScopesService()).isInstanceOf(ScopesService.class);
		assertThat(context.getUserService()).isInstanceOf(UserService.class);
		assertThat(context.getTokenUtils()).isInstanceOf(TokenUtils.class);
	}
}
