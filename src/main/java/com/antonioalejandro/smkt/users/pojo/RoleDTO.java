package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

public @Data class RoleDTO {

	/** id */
	@JsonProperty("id")
	private Long id;

	/** name */
	@JsonProperty("name")
	private String name;

}
