package com.antonioalejandro.smkt.users.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

/**
 * App Enviroment Class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
@Getter
@ToString
public class AppEnviroment {

	/** The default role id. */
	@Value("${default.params.roles.id}")
	private Long defaultRoleId;

	/** The super admin id. */
	@Value(value = "${superadmin.id}")
	private Long superAdminId;

	/** The scope super. */
	@Value("${scopes.super}")
	private String scopeSuper;

	/** The scope adm. */
	@Value("${scopes.adm}")
	private String scopeAdm;

	/** The scope read min. */
	@Value("${scopes.read-min}")
	private String scopeReadMin;

	/** The scope update self. */
	@Value("${scopes.update-self}")
	private String scopeUpdateSelf;

	/** The oauth id. */
	@Value("${oauth.id}")
	private String oauthId;

	/** The secret app. */
	@Value("${oauth.app-key-secret}")
	private String appKeySecret;

	/** The oauth path. */
	@Value("${oauth.path}")
	private String oauthPath;
}
