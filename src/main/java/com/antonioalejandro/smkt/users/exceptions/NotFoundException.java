package com.antonioalejandro.smkt.users.exceptions;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class NotFoundException extends RequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8969980942163744693L;
	
	@JsonProperty
	@NonNull
	private final String message;
	@JsonProperty
	private final String status = HttpStatus.NOT_FOUND.toString();

}
