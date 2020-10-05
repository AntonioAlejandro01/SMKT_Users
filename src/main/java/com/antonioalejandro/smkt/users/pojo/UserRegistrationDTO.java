package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;

public class UserRegistrationDTO {
	private String name;
	private String lastname;
	private String username;
	private String password;
	private String email;
	private String photo;
	public UserRegistrationDTO() {
		// TODO Auto-generated constructor stub
	}
	@JsonCreator
	public UserRegistrationDTO(String name, String lastname, String username, String password, String email,
			String photo) {
		super();
		this.name = name;
		this.lastname = lastname;
		this.username = username;
		this.password = password;
		this.email = email;
		this.photo = photo;
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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	
}
