package com.antonioalejandro.haas.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.antonioalejandro.haas.users.service.UserService;

@Configuration
public class PersistentContext {

	@Bean
	public UserService getUserService() {
		return new UserService();
	}

}
