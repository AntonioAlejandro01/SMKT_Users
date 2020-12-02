package com.antonioalejandro.smkt.users.converter;

import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;

public class RoleConverter implements GenericConverter<Role, RoleDTO> {

	@Override
	public RoleDTO apply(Role role) {
		RoleDTO roleDTO = new RoleDTO();
		
		roleDTO.setId(role.getId());
		roleDTO.setName(role.getName());
		
		return roleDTO;
	}

	public Role apply(RoleDTO roleDTO) {
		Role role = new Role();

		role.setId(roleDTO.getId());
		role.setName(roleDTO.getName());

		return role;
	}

}
