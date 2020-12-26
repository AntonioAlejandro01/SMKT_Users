/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.utils;

import java.util.regex.Pattern;

/**
 * The Class Constants.
 */
public class Constants {
	
	/** The Constant ADMIN_ROLE_NAME. */
	public static final String ADMIN_ROLE_NAME = "ADMIN";
	
	/** The Constant SUPERADMIN_ROLE_NAME. */
	public static final String SUPERADMIN_ROLE_NAME = "SUPERADMIN";
	
	/** The Constant USER_ROLE_NAME. */
	public static final String USER_ROLE_NAME = "USER";
	
	/** The Constant VALID_EMAIL_ADDRESS_REGEX. */
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	/** The Constant VALID_PASSWORD_REGEX. */
	public static final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
	
	/** The Constant TOKEN_KEY. */
	public static final String TOKEN_KEY = "Bearer ";
	
	/** The Constant TOKEN_FIELD_NAME. */
	public static final String TOKEN_FIELD_NAME = "token";
	
	/**
	 * Instantiates a new constants.
	 */
	private Constants() {}

}
