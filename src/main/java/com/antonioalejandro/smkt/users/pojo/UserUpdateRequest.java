package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class UserUpdateRequest {
	
	@JsonProperty
	private String name;
	
	@JsonProperty
	private String lastname;
	
	@JsonProperty
	private String username;
	
	@JsonProperty
	private String email;
	
	@JsonProperty
	private String password;
	
	@JsonProperty
	private RoleDTO role;
}
