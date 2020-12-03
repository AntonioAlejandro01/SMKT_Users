package com.antonioalejandro.smkt.users.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonioalejandro.smkt.users.converter.RoleConverter;
import com.antonioalejandro.smkt.users.dao.RoleDao;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoleService implements IRoleService{
	
	@Autowired
	private RoleConverter roleConverter;
	@Autowired
	private RoleDao repository;
	
	
	@Override
	public List<RoleDTO> getRoles() {
		log.debug("Call get all roles");
		return roleConverter.convert(StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()));
	}
	
	@Override
	public RoleDTO getRoleById(long id) {
		
		log.debug("Call get role: {}", id);
		
		final Optional<Role> oRole = repository.findById(id);
		
		return oRole.isPresent() ? roleConverter.convert(oRole.get()) : null;
	}

}
