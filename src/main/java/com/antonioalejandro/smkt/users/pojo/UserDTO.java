package com.antonioalejandro.smkt.users.pojo;


import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class UserDTO {
	
	private Long id;
	
	private String name;
	
	private String lastname;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private RoleDTO role;

	@JsonCreator
	public UserDTO(Long id, String name, String lastname, String username, String password, String email, RoleDTO role) {
		super();
		this.id = id;
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	
	
	
	
	
	
	
}
