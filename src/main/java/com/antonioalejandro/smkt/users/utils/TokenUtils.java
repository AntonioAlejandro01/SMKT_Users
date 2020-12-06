/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;

import com.antonioalejandro.smkt.users.pojo.TokenContent;

/**
 * The Class TokenUtils.
 */
public class TokenUtils {

	/** The Constant TOKEN_KEY. */
	public static final String TOKEN_KEY = "Bearer ";

	/** The app user. */
	@Value("${oauth.user}")
	private static String appUser;

	/** The app secret. */
	@Value("${oauth.secret}")
	private static String appSecret;

	/**
	 * Instantiates a new token utils.
	 */
	private TokenUtils() {
	}

	/**
	 * Gets the data token.
	 *
	 * @param token the token
	 * @return the data token
	 */
	public static TokenContent getDataToken(String token) {
		return new TokenContent(TOKEN_KEY + token);
	}

	/**
	 * Checks if is authorized.
	 *
	 * @param token           the token
	 * @param scopesPermitted the scopes permitted
	 * @return true, if is authorized
	 */
	public static boolean isAuthorized(String token, List<String> scopesPermitted) {

		return !token.isEmpty() && scopesPermitted != null;
	}
}
