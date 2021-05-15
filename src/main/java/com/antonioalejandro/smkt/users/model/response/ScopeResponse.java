/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.model.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * The Class ScopeResponse.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "ScopeResponse", description = "Response for scopes endpoints", subTypes = { String.class,
		List.class }, parent = GenericResponse.class)
public class ScopeResponse extends GenericResponse {

	/** The scopes. */
	
	/**
	 * Gets the scopes.
	 *
	 * @return the scopes
	 */
	@Getter
	@JsonProperty("scopes")
	private List<String> scopes;

	/**
	 * Instantiates a new scope response.
	 *
	 * @param status the status
	 * @param message the message
	 * @param scopes the scopes
	 */
	public ScopeResponse(HttpStatus status, String message, List<String> scopes) {
		super(status, message);
		this.scopes = scopes;
	}

	/**
	 * Instantiates a new scope response.
	 *
	 * @param status the status
	 * @param message the message
	 */
	public ScopeResponse(HttpStatus status, String message) {
		super(status, message);
		scopes = null;
	}
	
	/**
	 * Instantiates a new scope response.
	 *
	 * @param status the status
	 */
	public ScopeResponse(HttpStatus status) {
		super(status, null);
		scopes = null;
	}

	/**
	 * Instantiates a new scope response.
	 *
	 * @param scopes the scopes
	 */
	public ScopeResponse(List<String> scopes) {
		super(null, null);
		this.scopes = scopes;
	}

}
