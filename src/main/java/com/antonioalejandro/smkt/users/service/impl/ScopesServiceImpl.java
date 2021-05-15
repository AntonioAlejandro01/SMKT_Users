/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.model.entity.Role;
import com.antonioalejandro.smkt.users.model.entity.Scope;
import com.antonioalejandro.smkt.users.model.response.RoleResponse;
import com.antonioalejandro.smkt.users.model.response.ScopeResponse;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.ScopesService;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class ScopesService.
 */
@Slf4j
public class ScopesServiceImpl implements ScopesService {

	/** The role service. */
	@Autowired
	private RoleService roleService;

	/**
	 * Gets the scopes for role.
	 *
	 * @param roleId the role id
	 * @return the scopes for role
	 */
	@Override
	public ScopeResponse getScopesForRole(Long roleId) {

		RoleResponse roleResponse = roleService.getRoleById(roleId);

		Role role = roleResponse.getRole();

		if (role == null) {
			return new ScopeResponse(roleResponse.getHttpStatus(), roleResponse.getMessage());
		}

		List<String> scopes = role.getScopes().stream().map(Scope::getName).collect(Collectors.toList());
		if (scopes.isEmpty()) {
			return new ScopeResponse(HttpStatus.NO_CONTENT, "No Content");
		}
		log.debug("Roles for {} : {}", roleId,
				scopes.stream().reduce((acum, scope) -> acum + " " + scope).orElseGet(() -> "error scopes"));

		return new ScopeResponse(scopes);

	}

}
