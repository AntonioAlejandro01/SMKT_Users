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
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	/** The Constant VALID_PASSWORD_REGEX. */
	public static final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
	
	/** The Constant VALID_SCOPE_REGEX. */
	public static final Pattern VALID_SCOPE_REGEX = Pattern.compile("^[a-z]{3,}.[a-z]{3,}$");

	/** The Constant TOKEN_KEY. */
	public static final String TOKEN_KEY = "Bearer ";

	/** The Constant TOKEN_FIELD_NAME. */
	public static final String TOKEN_FIELD_NAME = "token";

	/** The Constant TEMPLATE_MESSAGE. */
	public static final String TEMPLATE_MESSAGE = "%s. ";

	/** The Constant TEMPLATE_FORMAT_SCOPE_NOT_VALID. */
	public static final String TEMPLATE_FORMAT_SCOPE_NOT_VALID = "Scope format not valid in %s";

	/** The Constant MESSAGE_EMPTY. */
	public static final String MESSAGE_EMPTY = "";

	/** The Constant ID_MANDATORY_MESSAGE. */
	public static final String ID_MANDATORY_MESSAGE = "Id is mandatory";
	
	/** The Constant ID_LESS_EQUAL_THAN_ZERO_MESSAGE. */
	public static final String ID_LESS_EQUAL_THAN_ZERO_MESSAGE = "Id can't be less or equal than zero";

	/** The Constant LASTNAME_LENGTH_NOT_VALID_MESSAGE. */
	public static final String LASTNAME_LENGTH_NOT_VALID_MESSAGE = "Lastname minimun length is 3";

	/** The Constant PASSWORD_MANDATORY_MESSAGE. */
	public static final String PASSWORD_MANDATORY_MESSAGE = "Password is mandatory";

	/** The Constant PASSWORD_NOT_VALID. */
	public static final String PASSWORD_NOT_VALID = "Password is not valid";

	/** The Constant PASSWORD_REQUIEREMENTS. */
	public static final String PASSWORD_REQUIEREMENTS = "The password minimun requirements are: one number, one upper case , one lower case, one special character ( !, @, #, (, &, ) ) and length 8~20";

	/** The Constant USERNAME_MANDATORY. */
	public static final String USERNAME_MANDATORY = "Username is mandatory";
	
	/** The Constant USERNAME_LENGTH_NOT_VALID. */
	public static final String USERNAME_LENGTH_NOT_VALID = "Username minimun length is 5";
	
	/** The Constant NAME_MANDATORY. */
	public static final String NAME_MANDATORY = "Name is mandatory";
	
	/** The Constant NAME_LENGTH_NOT_VALID. */
	public static final String NAME_LENGTH_NOT_VALID = "Name minimun length is 3";
	
	/** The Constant EMAIL_MANDATORY. */
	public static final String EMAIL_MANDATORY = "Email is mandatory";
	
	/** The Constant EMAIL_NOT_VALID. */
	public static final String EMAIL_NOT_VALID = "Email is not valid";

	/** The Constant USERNAME_VALID_LENGTH. */
	public static final int USERNAME_VALID_LENGTH = 5;
	
	/** The Constant NAME_VALID_LENGTH. */
	public static final int NAME_VALID_LENGTH = 3;
	
	/** The Constant LASTNAME_VALID_LENGTH. */
	public static final int LASTNAME_VALID_LENGTH = 3;
	
	/** The Constant MIN_ID_VALID. */
	public static final int MIN_ID_VALID = 1;
	
	/** The Constant LIST_SCOPES_MANDATORY. */
	public static final String LIST_SCOPES_MANDATORY = "List of scopes is mandatory";
	

	/**
	 * Instantiates a new constants.
	 */
	private Constants() {
	}

}
