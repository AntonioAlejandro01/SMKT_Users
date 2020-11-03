package com.antonioalejandro.smkt.users.pojo;


import com.fasterxml.jackson.annotation.JsonCreator;

public class UserDTO {
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
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public RoleDTO getRole() {
		return role;
	}
	public void setRole(RoleDTO role) {
		this.role = role;
	}
	
	
	
	
	
	
	
	
}
