package com.antonioalejandro.smkt.users.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class RoleDTO {

	/** id */
	@JsonProperty("id")
	private Long id;

	/** name */
	@JsonProperty("name")
	private String name;

	/** scopes */
	@JsonProperty("scopes")
	private List<String> scopes;

}
