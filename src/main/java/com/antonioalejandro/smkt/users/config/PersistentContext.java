/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.ScopesService;
import com.antonioalejandro.smkt.users.service.UserService;

/**
 * The Class PersistentContext.
 */
@Configuration
public class PersistentContext {

	/**
	 * Gets the user service.
	 *
	 * @return the user service
	 */
	@Bean
	public UserService getUserService() {
		return new UserService();
	}

	/**
	 * Gets the role service.
	 *
	 * @return the role service
	 */
	@Bean
	public RoleService getRoleService() {
		return new RoleService();
	}

	/**
	 * Gets the encoder.
	 *
	 * @return the encoder
	 */
	@Bean
	public BCryptPasswordEncoder getEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * Gets the scopes service.
	 *
	 * @return the scopes service
	 */
	@Bean
	public ScopesService getScopesService() {
		return new ScopesService();
	}

}
