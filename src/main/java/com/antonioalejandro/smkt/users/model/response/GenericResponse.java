package com.antonioalejandro.smkt.users.model.response;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import lombok.Getter;

/**
 * Generic Response Class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
@ApiModel(value = "Generic Response", description = "Response for any endpoints", subTypes = { String.class })
@Getter
public class GenericResponse {

	/** The status. */
	@JsonProperty("status")
	private Integer status;

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
			this.status = status.value();
			this.message = message == null ? status.getReasonPhrase() : message;
		} else {
			this.message = message;
		}
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
