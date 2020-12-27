/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * The Class Validations.
 */
public class Validations {

	/**
	 * Instantiates a new validations.
	 */
	private Validations() {
	}

	/**
	 * Validate email.
	 *
	 * @param email the email
	 * @return the string
	 */
	public static String validateEmail(String email) {

		if (!isFieldPresent(email)) {
			return formatMessage(Constants.EMAIL_MANDATORY);
		}

		if (!validateEmailRegex(email)) {
			return formatMessage(Constants.EMAIL_NOT_VALID);
		}

		return Constants.MESSAGE_EMPTY;

	}

	/**
	 * Validate email regex.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	public static boolean validateEmailRegex(String email) {
		return Constants.VALID_EMAIL_ADDRESS_REGEX.matcher(email).find();
	}

	/**
	 * Validate name.
	 *
	 * @param name the name
	 * @return the string
	 */
	public static String validateName(String name) {

		if (!isFieldPresent(name)) {
			return formatMessage(Constants.NAME_MANDATORY);
		}

		if (!isLengthFieldOk(name, Constants.NAME_VALID_LENGTH)) {
			return formatMessage(Constants.NAME_LENGTH_NOT_VALID);
		}

		return Constants.MESSAGE_EMPTY;
	}

	/**
	 * Validate username.
	 *
	 * @param username the username
	 * @return the string
	 */
	public static String validateUsername(String username) {

		if (!isFieldPresent(username)) {
			return formatMessage(Constants.USERNAME_MANDATORY);
		}

		if (!isLengthFieldOk(username, Constants.USERNAME_VALID_LENGTH)) {
			return formatMessage(Constants.USERNAME_LENGTH_NOT_VALID);
		}

		return Constants.MESSAGE_EMPTY;
	}

	/**
	 * Validate password.
	 *
	 * @param password the password
	 * @return the string
	 */
	public static String validatePassword(String password) {

		if (!isFieldPresent(password)) {
			return formatMessage(Constants.PASSWORD_MANDATORY_MESSAGE);
		}

		if (!validatePasswordRegex(password)) {
			return formatMessage(Constants.PASSWORD_NOT_VALID) + formatMessage(Constants.PASSWORD_REQUIEREMENTS);
		}

		return Constants.MESSAGE_EMPTY;
	}

	/**
	 * Validate password regex.
	 *
	 * @param password the password
	 * @return true, if successful
	 */
	public static boolean validatePasswordRegex(String password) {
		return Constants.VALID_PASSWORD_REGEX.matcher(password).find();
	}

	/**
	 * Validate lastname.
	 *
	 * @param lastname the lastname
	 * @return the string
	 */
	public static String validateLastname(String lastname) {
		if (isFieldPresent(lastname) && !isLengthFieldOk(lastname, Constants.LASTNAME_VALID_LENGTH)) {
			return formatMessage(Constants.LASTNAME_LENGTH_NOT_VALID_MESSAGE);
		}
		return Constants.MESSAGE_EMPTY;
	}

	/**
	 * Validate id.
	 *
	 * @param id          the id
	 * @param isMandatory the is mandatory
	 * @return the string
	 */
	public static String validateId(Long id, boolean isMandatory) {
		if (isMandatory && id == null) {
			return formatMessage(Constants.ID_MANDATORY_MESSAGE);
		}
		if (id < Constants.MIN_ID_VALID) {
			return formatMessage(Constants.ID_LESS_EQUAL_THAN_ZERO_MESSAGE);
		}
		return Constants.MESSAGE_EMPTY;
	}

	/**
	 * Format message.
	 *
	 * @param message the message
	 * @return the string
	 */
	public static String formatMessage(String message) {
		return String.format(Constants.TEMPLATE_MESSAGE, message);
	}

	/**
	 * Checks if is field present.
	 *
	 * @param field the field
	 * @return true, if is field present
	 */
	public static boolean isFieldPresent(String field) {
		return field != null && !field.isBlank();
	}

	/**
	 * Checks if is length field ok.
	 *
	 * @param field  the field
	 * @param length the length
	 * @return true, if is length field ok
	 */
	public static boolean isLengthFieldOk(String field, int length) {
		return field.length() >= length;
	}

	/**
	 * Validate app key.
	 *
	 * @param appKey the app key
	 * @return true, if successful
	 */
	public static boolean validateAppKey(String appKey, String secret) {
		return appKey != null && !secret.equals(DigestUtils.sha256Hex(appKey));
	}

	/**
	 * Validate null fields.
	 *
	 * @param filter the filter
	 * @param value  the value
	 * @return true, if successful
	 */
	public static boolean validateNullFields(String filter, String value) {
		return filter == null || value == null;

	}
}
