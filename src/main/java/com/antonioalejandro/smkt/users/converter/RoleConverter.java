package com.antonioalejandro.smkt.users.converter;

import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;

public class RoleConverter implements GenericConverter<Role, RoleDTO> {
	
	@Override
	public RoleDTO apply(Role arg0) {
		return new RoleDTO(arg0.getId(), arg0.getName());
	}
	
	public Role apply(RoleDTO arg0) {
		return new Role(arg0.getId(), arg0.getName());
	}

}
