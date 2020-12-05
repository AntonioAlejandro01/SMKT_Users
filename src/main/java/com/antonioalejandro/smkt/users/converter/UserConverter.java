package com.antonioalejandro.smkt.users.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.exceptions.NotFoundException;
import com.antonioalejandro.smkt.users.exceptions.RequestException;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;
import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.service.IRoleService;

public class UserConverter implements GenericConverter<User, UserDTO> {

	@Autowired
	private RoleConverter roleConverter;

	@Autowired
	private IRoleService roleService;

	@Override
	public UserDTO apply(User user) {

		UserDTO userDTO = new UserDTO();

		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setLastname(user.getLastname());
		userDTO.setPassword(user.getPassword());
		userDTO.setUsername(user.getUsername());
		userDTO.setEmail(user.getEmail());
		RoleDTO roleDTO = roleConverter.convert(user.getRole());

		userDTO.setRole(roleDTO.getName());

		return userDTO;
	}

	public User apply(UserDTO userDTO) throws RequestException{

		User user = new User();

		user.setId(userDTO.getId());
		user.setName(userDTO.getName());
		user.setLastname(userDTO.getLastname());
		user.setPassword(userDTO.getPassword());
		user.setUsername(userDTO.getUsername());
		user.setEmail(userDTO.getEmail());

		Role role = roleConverter.apply(roleService.getRoleByName(userDTO.getRole()));

		user.setRole(role);

		return user;

	}

}
