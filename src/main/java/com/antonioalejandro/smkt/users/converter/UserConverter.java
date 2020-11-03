package com.antonioalejandro.smkt.users.converter;

import org.springframework.beans.factory.annotation.Autowired;

import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.UserDTO;

public class UserConverter implements GenericConverter<User, UserDTO>{
	
	@Autowired
	private RoleConverter roleConverter;
	
	@Override
	public UserDTO apply(User arg0) {
		return new UserDTO(arg0.getId(), arg0.getName(), arg0.getLastname(), arg0.getUsername(), arg0.getPassword(), arg0.getEmail(),roleConverter.convert(arg0.getRole()));
	}
	
	public User apply(UserDTO arg0) {
		return new User(arg0.getId(), arg0.getName(), arg0.getLastname(), arg0.getUsername(), arg0.getEmail(), arg0.getPassword(),roleConverter.apply(arg0.getRole()));
	}

}
