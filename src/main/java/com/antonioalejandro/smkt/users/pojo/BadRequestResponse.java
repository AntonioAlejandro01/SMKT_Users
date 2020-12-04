package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BadRequestResponse {
	@JsonProperty
	private String message;
	@JsonProperty
	private String status;
}
