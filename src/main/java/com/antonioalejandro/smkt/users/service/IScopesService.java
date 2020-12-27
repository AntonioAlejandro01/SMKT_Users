/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service;

import com.antonioalejandro.smkt.users.pojo.response.ScopeResponse;

/**
 * The Interface IScopesService.
 */
public interface IScopesService {

	/**
	 * Gets the scopes for role.
	 *
	 * @param roleId the role id
	 * @return the scopes for role
	 */
	public ScopeResponse getScopesForRole(Long roleId);

}
