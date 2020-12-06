/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.pojo.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.entity.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class UserResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@ToString
public class UserResponse extends GenericResponse {

	/** The user. */
	@JsonProperty("user")
	private User user;

	/** The users. */
	@JsonProperty("users")
	private List<User> users;

	/**
	 * Instantiates a new user response.
	 *
	 * @param status  the status
	 * @param message the message
	 * @param user    the user
	 */
	public UserResponse(HttpStatus status, String message, User user) {
		super(status, message);
		this.user = user;
		users = null;
	}

	/**
	 * Instantiates a new user response.
	 *
	 * @param status  the status
	 * @param message the message
	 * @param users   the users
	 */
	public UserResponse(HttpStatus status, String message, List<User> users) {
		super(status, message);
		this.users = users;
		user = null;
	}

	/**
	 * Instantiates a new user response.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public UserResponse(HttpStatus status, String message) {
		super(status, message);
		user = null;
		users = null;
	}

	/**
	 * Instantiates a new user response.
	 *
	 * @param users the users
	 */
	public UserResponse(List<User> users) {
		super(null, null);
		this.users = users;
		user = null;
	}

	/**
	 * Instantiates a new user response.
	 *
	 * @param user the user
	 */
	public UserResponse(User user) {
		super(null, null);
		users = null;
		this.user = user;
	}

}
