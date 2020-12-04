package com.antonioalejandro.smkt.users.service;

import java.util.List;

import com.antonioalejandro.smkt.users.pojo.RoleDTO;

public interface IRoleService {
	public List<RoleDTO> getRoles();

	public RoleDTO getRoleById(final long id);

	public RoleDTO getRoleByName(final String name);
	
	public List<String> getScopesByIdRole(final long idRole);
	
}
