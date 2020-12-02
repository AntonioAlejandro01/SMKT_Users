package com.antonioalejandro.smkt.users.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public @Data class RoleDTO {
	
	/** id */
	private Long id;
	
	/** name */
	private String name;

	/**
	 * @param id
	 * @param name
	 */
	@JsonCreator
	public RoleDTO(Long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

}
