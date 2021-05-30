package com.antonioalejandro.smkt.users.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.antonioalejandro.smkt.users.service.impl.RoleServiceImpl;
import com.antonioalejandro.smkt.users.service.impl.ScopesServiceImpl;
import com.antonioalejandro.smkt.users.service.impl.UserServiceImpl;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

/**
 * Persistent Context Class
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
@Configuration
public class PersistentContext {

	/**
	 * Gets the user service.
	 *
	 * @return the user service
	 */
	@Bean
	public UserServiceImpl getUserService() {
		return new UserServiceImpl();
	}

	/**
	 * Gets the role service.
	 *
	 * @return the role service
	 */
	@Bean
	public RoleServiceImpl getRoleService() {
		return new RoleServiceImpl();
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
	public ScopesServiceImpl getScopesService() {
		return new ScopesServiceImpl();
	}

	/**
	 * Gets the token utils.
	 *
	 * @return the token utils
	 */
	@Bean
	public TokenUtils getTokenUtils() {
		return new TokenUtils();
	}
	
	/**
	 * Gets the app enviroment.
	 *
	 * @return the app enviroment
	 */
	@Bean
	public AppEnviroment getAppEnviroment() {
		return new AppEnviroment();
	}

}
