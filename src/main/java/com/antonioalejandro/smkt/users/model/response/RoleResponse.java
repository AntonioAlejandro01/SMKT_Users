package com.antonioalejandro.smkt.users.model.response;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.model.entity.Role;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * Role Response class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@ApiModel(value = "RoleResponse", description = "Response for roles endpoints")
public class RoleResponse extends GenericResponse {

	/** The role. */
	@ApiModelProperty(value = "Role", dataType = "Role", position = 3, allowEmptyValue = true)
	@JsonProperty("role")
	private Role role;

	/** The roles. */
	@ApiModelProperty(value = "List of roles", dataType = "List<Role>", position = 4, allowEmptyValue = true)
	@JsonProperty("roles")
	private List<Role> roles;

	/**
	 * Instantiates a new role response.
	 *
	 * @param status  the status
	 * @param message the message
	 * @param role    the role
	 */
	public RoleResponse(HttpStatus status, String message, Role role) {
		super(status, message);
		this.role = role;
		roles = null;
	}

	/**
	 * Instantiates a new role response.
	 *
	 * @param status  the status
	 * @param message the message
	 * @param roles   the roles
	 */
	public RoleResponse(HttpStatus status, String message, List<Role> roles) {
		super(status, message);
		role = null;
		this.roles = roles;
	}

	/**
	 * Instantiates a new role response.
	 *
	 * @param status  the status
	 * @param message the message
	 */
	public RoleResponse(HttpStatus status, String message) {
		super(status, message);
		role = null;
		roles = null;
	}

	/**
	 * Instantiates a new role response.
	 *
	 * @param status the status
	 */
	public RoleResponse(HttpStatus status) {
		super(status, null);
		role = null;
		roles = null;
	}

	/**
	 * Instantiates a new role response.
	 *
	 * @param role the role
	 */
	public RoleResponse(Role role) {
		super(null, null);
		this.role = role;
		roles = null;
	}

	/**
	 * Instantiates a new role response.
	 *
	 * @param roles the roles
	 */
	public RoleResponse(List<Role> roles) {
		super(null, null);
		role = null;
		this.roles = roles;
	}
}
