package com.antonioalejandro.smkt.users.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ScopesService implements IScopesService {

	@Autowired
	private IRoleService roleService;
	
	@Override
	public List<String> getScopesForRole(Long roleId) {
		
		List<Scopes> scopes = roleService.getRoleById(roleId).get
	}

}
