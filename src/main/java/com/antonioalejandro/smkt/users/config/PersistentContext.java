package com.antonioalejandro.smkt.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antonioalejandro.smkt.users.converter.RoleConverter;
import com.antonioalejandro.smkt.users.converter.UserConverter;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.UserService;

@Configuration
public class PersistentContext {

	@Bean
	public UserService getUserService() {
		return new UserService();
	}
	@Bean
	public RoleService getRoleService() {
		return new RoleService();
	}
	
	@Bean
	public RoleConverter getRoleConverter() {
		return new RoleConverter();
	}
	
	@Bean
	public UserConverter getUserConcverter() {
		return new UserConverter();
	}
	
	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

}
