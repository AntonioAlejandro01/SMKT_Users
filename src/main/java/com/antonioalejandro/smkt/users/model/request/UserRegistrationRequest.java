package com.antonioalejandro.smkt.users.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Registration Request Class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
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
