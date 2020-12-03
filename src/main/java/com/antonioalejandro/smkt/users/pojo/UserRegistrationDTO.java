package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

public @Data class UserRegistrationDTO {

	@JsonProperty("name")
	private String name;

	@JsonProperty("lastname")
	private String lastname;

	@JsonProperty("username")
	private String username;

	@JsonIgnore
	private String password;

	@JsonProperty("email")
	private String email;
}
