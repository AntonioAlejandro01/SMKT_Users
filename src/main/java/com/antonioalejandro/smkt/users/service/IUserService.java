package com.antonioalejandro.smkt.users.service;

import java.util.List;

import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationDTO;
import com.antonioalejandro.smkt.users.pojo.UserResponse;
import com.antonioalejandro.smkt.users.pojo.UserUpdateRequest;

public interface IUserService {

	public List<UserDTO> getUsers();

	public List<UserDTO> getUsersByEmail(String email);

	public UserDTO getUserById(long id);

	public UserResponse updateUser(UserUpdateRequest userUpdateRequest, Long id);

	public UserDTO create(UserRegistrationDTO user);

	public UserResponse delete(Long id);

}
