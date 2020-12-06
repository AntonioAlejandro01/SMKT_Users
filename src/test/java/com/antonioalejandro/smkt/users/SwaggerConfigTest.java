package com.antonioalejandro.smkt.users;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.config.SwaggerConfig;

import springfox.documentation.spring.web.plugins.Docket;

class SwaggerConfigTest {

	@Test
	void test() throws Exception {
		SwaggerConfig config = new SwaggerConfig();

		assertThat(config.usersApi()).isInstanceOf(Docket.class);
	}

}
