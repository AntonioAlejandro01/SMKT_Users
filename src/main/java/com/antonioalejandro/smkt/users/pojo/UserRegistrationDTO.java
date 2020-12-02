package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;

public @Data class UserRegistrationDTO {
	
	private String name;

	private String lastname;

	private String username;

	private String password;

	private String email;

	@JsonCreator
	public UserRegistrationDTO(String name, String lastname, String username, String password, String email) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
	}
}
