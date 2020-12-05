package com.antonioalejandro.smkt.users.exceptions;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BadRequestException extends RequestException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6789504812439082230L;
	
	@JsonProperty
	@NonNull
	private final String message;
	@JsonProperty
	private final String status = HttpStatus.BAD_REQUEST.toString();
	
}
