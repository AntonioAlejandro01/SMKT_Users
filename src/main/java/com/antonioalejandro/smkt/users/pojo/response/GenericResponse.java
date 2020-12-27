/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.pojo.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * The Class GenericResponse.
 */
@ApiModel(value = "Generic Response", description = "Response for any endpoints", subTypes = { String.class })
@Getter
public class GenericResponse {

	/** The status. */
	@JsonProperty("status")
	private String status;

	/** The message. */
	@JsonProperty("message")
	private String message;

	/** The http status. */
	@JsonIgnore
	private HttpStatus httpStatus;

	/**
	 * Instantiates a new generic response.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public GenericResponse(HttpStatus status, String message) {
		this.httpStatus = status;
		if (status != null) {
			this.status = status.toString();
			this.message = message == null ? status.getReasonPhrase():message;
		}
		this.message = message;
	}

	/**
	 * Have data.
	 *
	 * @return true, if successful
	 */
	public boolean haveData() {
		return httpStatus == null;
	}

}
