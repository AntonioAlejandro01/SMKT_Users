package com.antonioalejandro.smkt.users.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

/**
 * Token Data Class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */

@Getter
public class TokenData {

	/** The username. */
	@JsonProperty("user_name")
	private String username;

	/** The scope. */
	private List<String> scope;

	/** The name. */
	private String name;

	/** The active. */
	private boolean active;

	/** The exp. */
	private Long exp;

	/** The authorities. */
	private List<String> authorities;

	/** The jti. */
	private String jti;

	/** The email. */
	private String email;

	/** The client id. */
	@JsonProperty("client_id")
	private String clientId;

	/** The lastname. */
	private String lastname;

	/** The username C. */
	@JsonProperty("username")
	private String usernameC;

}
