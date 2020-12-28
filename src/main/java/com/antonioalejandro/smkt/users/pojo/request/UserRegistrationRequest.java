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
 * The Class UserRegistrationRequest.
 */
@Getter
@Setter
@NoArgsConstructor
public class UserRegistrationRequest {

	/** The name. */
	@JsonProperty
	private String name;

	/** The lastname. */
	@JsonProperty(value = "lastname", required = false)
	private String lastname;

	/** The email. */
	@JsonProperty
	private String email;

	/** The username. */
	@JsonProperty
	private String username;

	/** The password. */
	@JsonProperty
	private String password;

}
