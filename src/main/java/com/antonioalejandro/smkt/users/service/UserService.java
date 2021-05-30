package com.antonioalejandro.smkt.users.service;

import com.antonioalejandro.smkt.users.model.TokenData;
import com.antonioalejandro.smkt.users.model.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.model.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.model.response.UserResponse;

/**
 * User Service Interface
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0s
 */
public interface UserService {

	/**
	 * Gets the users.
	 *
	 * @param tokenData the token data
	 * @return the users
	 */
	public UserResponse getUsers(TokenData tokenData);

	/**
	 * Gets the user by email or username.
	 *
	 * @param value     the value
	 * @param isEmail   the is email
	 * @param tokenData the token data
	 * @return the user by email or username
	 */
	public UserResponse getUserByEmailOrUsername(String value, boolean isEmail, TokenData tokenData);

	/**
	 * Gets the user by username key.
	 *
	 * @param value the value
	 * @return the user by username key
	 */
	public UserResponse getUserByUsernameKey(String value);

	/**
	 * Gets the user by id.
	 *
	 * @param id        the id
	 * @param tokenData the token data
	 * @return the user by id
	 */
	public UserResponse getUserById(long id, TokenData tokenData);

	/**
	 * Update user.
	 *
	 * @param userUpdateRequest the user update request
	 * @param id                the id
	 * @param tokenData         the token data
	 * @return the user response
	 */
	public UserResponse updateUser(UserUpdateRequest userUpdateRequest, long id, TokenData tokenData);

	/**
	 * Creates the user.
	 *
	 * @param user      the user
	 * @param tokenData the token data
	 * @return the user response
	 */
	public UserResponse createUser(UserRegistrationRequest user, TokenData tokenData);

	/**
	 * Delete user.
	 *
	 * @param id        the id
	 * @param tokenData the token data
	 * @return the user response
	 */
	public UserResponse deleteUser(long id, TokenData tokenData);

}
