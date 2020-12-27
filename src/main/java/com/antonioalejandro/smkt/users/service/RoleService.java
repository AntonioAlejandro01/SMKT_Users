/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.dao.RoleDao;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.Scope;
import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class RoleService.
 */
@Slf4j
public class RoleService implements IRoleService {

	/** The repository. */
	@Autowired
	private RoleDao repository;

	@Autowired
	private IScopesService scopeService;

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	@Override
	public RoleResponse getRoles() {
		log.debug("Call get all roles");
		return new RoleResponse(
				StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()));
	}

	/**
	 * Gets the role by id.
	 *
	 * @param id the id
	 * @return the role by id
	 */
	@Override
	public RoleResponse getRoleById(long id) {

		log.debug("Call get role: {}", id);
		Optional<Role> role = repository.findById(id);
		if (!role.isPresent()) {
			return new RoleResponse(HttpStatus.NOT_FOUND);
		}
		return new RoleResponse(role.get());
	}

	/**
	 * Gets the role by name.
	 *
	 * @param name the name
	 * @return the role by name
	 */
	@Override
	public RoleResponse getRoleByName(String name) {
		log.debug("Call get Role by name: {}", name);

		Role role = repository.findByName(name);
		if (role == null) {
			return new RoleResponse(HttpStatus.NOT_FOUND);
		}
		return new RoleResponse(role);
	}

	/**
	 * Adds the scopes to role.
	 *
	 * @param id the id
	 * @param scopes the scopes
	 * @return the role response
	 */
	@Override
	public RoleResponse addScopesToRole(long id, List<String> scopes) {
		log.debug("Call add scope to Role with id: {}", id);
		Optional<Role> oRole = repository.findById(id);
		if (oRole.isEmpty()) {
			return new RoleResponse(HttpStatus.NOT_FOUND);
		}
		Role role = oRole.get();
		Set<Scope> sScope = role.getScopes();
		scopes.stream().map(scopeService::addScope).forEach(sScope::add);
		return new RoleResponse(repository.save(role));
	}

}
