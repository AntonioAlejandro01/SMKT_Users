package com.antonioalejandro.smkt.users.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * User Update Request Class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
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
