package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserRegistrationRequest {
	
	@JsonProperty
	private String name;
	
	@JsonProperty(value="lastname",required = false)
	private String lastname;
	
	@JsonProperty
	private String email;

	@JsonProperty
	private String username;
	
	@JsonProperty
	private String password;
	
	
}
