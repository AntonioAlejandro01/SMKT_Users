package com.antonioalejandro.smkt.users.service;

import com.antonioalejandro.smkt.users.model.response.RoleResponse;

/**
 * Role Service Implementation
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
public interface RoleService {

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public RoleResponse getRoles();

	/**
	 * Gets the role by id.
	 *
	 * @param id the id
	 * @return the role by id
	 */
	public RoleResponse getRoleById(final long id);

	/**
	 * Gets the role by name.
	 *
	 * @param name the name
	 * @return the role by name
	 */
	public RoleResponse getRoleByName(final String name);

}
