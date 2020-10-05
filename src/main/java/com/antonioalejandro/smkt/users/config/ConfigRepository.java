package com.antonioalejandro.smkt.users.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;

import com.antonioalejandro.smkt.users.entity.User;

@Configuration
public class ConfigRepository implements RepositoryRestConfigurer {

	@Override
	public void configureRepositoryRestConfiguration(final RepositoryRestConfiguration config) {
		config.exposeIdsFor(User.class);
	}
}
