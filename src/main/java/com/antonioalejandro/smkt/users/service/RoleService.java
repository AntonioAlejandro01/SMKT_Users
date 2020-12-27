/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import com.antonioalejandro.smkt.users.dao.RoleDao;
import com.antonioalejandro.smkt.users.entity.Role;
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

}
