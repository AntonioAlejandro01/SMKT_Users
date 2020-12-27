/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service;

import java.util.List;

import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;

/**
 * The Interface IRoleService.
 */
public interface IRoleService {

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
	
	/**
	 * Adds the scopes to role.
	 *
	 * @param id the id
	 * @param scopes the scopes
	 * @return the role response
	 */
	public RoleResponse addScopesToRole(final long id, final List<String> scopes);

}
