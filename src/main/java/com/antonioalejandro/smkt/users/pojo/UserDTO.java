package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class UserDTO {

	@JsonProperty("id")
	private Long id;
	
	@JsonProperty("name")
	private String name;
	
	@JsonProperty(value = "lastname", required = false)
	private String lastname;
	
	@JsonProperty("username")
	private String username;

	@JsonIgnore
	private String password;

	@JsonProperty("email")
	private String email;

	@JsonProperty("role")
	private String role;

}
