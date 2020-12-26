/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.config;

import org.springframework.beans.factory.annotation.Value;

import lombok.Getter;
import lombok.ToString;

/**
 * The Class AppEnviroment.
 */
@Getter
@ToString
public class AppEnviroment {

	/** The default role id. */
	@Value("${default.params.roles.id}")
	private long defaultRoleId;

	/** The super admin id. */
	@Value(value = "${superadmin.id}")
	private long superAdminId;

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

	/** The app user. */
	@Value("${oauth.user}")
	private String appUser;

	/** The app secret. */
	@Value("${oauth.secret}")
	private String appSecret;

	/** The oauth id. */
	@Value("${oauth.id}")
	private String oauthId;

	/** The secret app. */
	@Value("${oauth.app-key-secret}")
	private String secretApp;

	/** The oauth path. */
	@Value("${oauth.path}")
	private String oauthPath;
}
