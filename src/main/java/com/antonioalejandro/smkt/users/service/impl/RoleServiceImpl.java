package com.antonioalejandro.smkt.users.service.impl;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.dao.RoleDao;
import com.antonioalejandro.smkt.users.model.entity.Role;
import com.antonioalejandro.smkt.users.model.response.RoleResponse;
import com.antonioalejandro.smkt.users.service.RoleService;

import lombok.extern.slf4j.Slf4j;

/**
 * Role Service Implementation
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
@Slf4j
public class RoleServiceImpl implements RoleService {

	/** The repository. */
	@Autowired
	private RoleDao repository;

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	@Override
	public RoleResponse getRoles() {
		log.info("Call get all roles");
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
		log.info("Call get role: {}", id);

		Optional<Role> role = repository.findById(id);

		if (role.isEmpty()) {
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
		log.info("Call get Role by name: {}", name);

		Optional<Role> oRole = Optional.ofNullable(repository.findByName(name));

		if (oRole.isEmpty()) {
			return new RoleResponse(HttpStatus.NOT_FOUND);
		}

		return new RoleResponse(oRole.get());
	}

}
