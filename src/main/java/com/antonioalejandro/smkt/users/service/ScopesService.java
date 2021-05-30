package com.antonioalejandro.smkt.users.service;

import com.antonioalejandro.smkt.users.model.response.ScopeResponse;

/**
 * Scope Service Interface
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
public interface ScopesService {

	/**
	 * Gets the scopes for role.
	 *
	 * @param roleId the role id
	 * @return the scopes for role
	 */
	public ScopeResponse getScopesForRole(Long roleId);

}
