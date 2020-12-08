/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.pojo.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Instantiates a new user update request.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserUpdateRequest {

	/** The name. */
	@JsonProperty
	private String name;

	/** The lastname. */
	@JsonProperty
	private String lastname;

	/** The username. */
	@JsonProperty
	private String username;

	/** The email. */
	@JsonProperty
	private String email;

	/** The password. */
	@JsonProperty
	private String password;

	/** The role. */
	@JsonProperty
	private String role;
}
