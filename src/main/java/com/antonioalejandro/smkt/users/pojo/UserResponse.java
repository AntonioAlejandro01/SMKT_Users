package com.antonioalejandro.smkt.users.pojo;

import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.entity.User;

public class UserResponse {
	
	private HttpStatus status;
	private String message;
	private User user;
	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	public UserResponse(HttpStatus status, String message, User user) {
		super();
		this.status = status;
		this.message = message;
		this.user = user;
	}
	public UserResponse(HttpStatus status, User user) {
		super();
		this.status = status;
		this.user = user;
	}
	public UserResponse(HttpStatus status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	
	public UserResponse() {
	}
	
	

}
