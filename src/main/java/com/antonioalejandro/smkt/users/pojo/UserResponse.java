package com.antonioalejandro.smkt.users.pojo;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class UserResponse {
	
	@JsonProperty("status")
	@NonNull
	private HttpStatus status;
	
	@JsonProperty("message")
	@NonNull
	private String message;
	
	@JsonProperty
	private UserDTO user;
	

}
