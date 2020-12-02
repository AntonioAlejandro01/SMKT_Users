package com.antonioalejandro.smkt.users.service;

import java.util.List;

import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationDTO;
import com.antonioalejandro.smkt.users.pojo.UserResponse;

public interface IUserService {

	public List<UserDTO> getUsers();

	public List<UserDTO> getUsersByEmail(String email);

	public UserDTO getUserById(long id);

	public UserResponse updateUser(UserRegistrationDTO registrationDTO, Long id);

	public UserDTO create(UserRegistrationDTO user);

	public UserResponse delete(Long id);

}
