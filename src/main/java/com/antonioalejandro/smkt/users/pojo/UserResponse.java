package com.antonioalejandro.smkt.users.pojo;

import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public @Data class UserResponse {
	
	@NonNull
	private HttpStatus status;
	
	@NonNull
	private String message;
	
	private User user;
	

}
