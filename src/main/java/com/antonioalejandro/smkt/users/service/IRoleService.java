package com.antonioalejandro.smkt.users.service;

import java.util.List;

import com.antonioalejandro.smkt.users.exceptions.RequestException;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;

public interface IRoleService {
	public List<RoleDTO> getRoles();

	public RoleDTO getRoleById(final long id) throws RequestException;

	public RoleDTO getRoleByName(final String name) throws RequestException;
	
	public List<String> getScopesByIdRole(final long idRole) throws RequestException;
	
}
