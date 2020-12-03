package com.antonioalejandro.smkt.users.pojo;

import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class UserResponse {
	
	@JsonProperty("status")
	@NonNull
	private HttpStatus status;
	
	@JsonProperty("message")
	@NonNull
	private String message;
	
	private User user;
	

}
