/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service;

import com.antonioalejandro.smkt.users.pojo.TokenData;
import com.antonioalejandro.smkt.users.pojo.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;

/**
 * The Interface IUserService.
 */
public interface IUserService {

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public UserResponse getUsers(TokenData tokenData);

	/**
	 * Gets the user by email or username.
	 *
	 * @param value   the value
	 * @param isEmail the is email
	 * @return the user by email or username
	 */
	public UserResponse getUserByEmailOrUsername(String value, boolean isEmail, TokenData tokenData);
	
	public UserResponse getUserByUsernameKey(String value);

	/**
	 * Gets the user by id.
	 *
	 * @param id the id
	 * @return the user by id
	 */
	public UserResponse getUserById(long id, TokenData tokenData);

	/**
	 * Update user.
	 *
	 * @param userUpdateRequest the user update request
	 * @param id                the id
	 * @return the user response
	 */
	public UserResponse updateUser(UserUpdateRequest userUpdateRequest, long id, TokenData tokenData);

	/**
	 * Creates the user.
	 *
	 * @param user the user
	 * @return the user response
	 */
	public UserResponse createUser(UserRegistrationRequest user, TokenData tokenData);

	/**
	 * Delete user.
	 *
	 * @param id the id
	 * @return the user response
	 */
	public UserResponse deleteUser(long id, TokenData tokenData);

}
