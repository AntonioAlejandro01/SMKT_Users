package com.antonioalejandro.smkt.users.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonioalejandro.smkt.users.converter.RoleConverter;
import com.antonioalejandro.smkt.users.dao.RoleDao;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.Scope;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleService implements IRoleService {

	@Autowired
	private RoleConverter roleConverter;
	@Autowired
	private RoleDao repository;

	@Override
	public List<RoleDTO> getRoles() {
		log.debug("Call get all roles");
		return roleConverter
				.convert(StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()));
	}

	@Override
	public RoleDTO getRoleById(long id) {

		log.debug("Call get role: {}", id);

		return roleConverter.convert(repository.findById(id).orElseGet(() -> null));
	}

	@Override
	public RoleDTO getRoleByName(String name) {
		log.debug("Call get Role by name: {}", name);

		Role role = repository.findByName(name);

		return roleConverter.convert(role);
	}

	@Override
	public List<String> getScopesByIdRole(long idRole) {
		log.debug("Scopes for role: " + idRole);

		Optional<Role> role = repository.findById(idRole);

		return role.isPresent() ? role.get().getScopes().stream().map(Scope::getName).collect(Collectors.toList())
				: new ArrayList<>();

	}

}
