package com.antonioalejandro.smkt.users.exceptions;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UnauthorizedException extends RequestException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2350908682864953283L;

	@JsonProperty
	private final String status = HttpStatus.UNAUTHORIZED.toString();

}
