package com.antonioalejandro.haas.users.service;

import java.util.List;

import com.antonioalejandro.haas.users.entity.User;

public interface IUserService {

	public List<User> getUsers();

	public List<User> getUsersByEmail(String email);

	public User getUserById(long id);

	public String UpdateUser(User user);

	public User create(User user);

	public String delete(Long id);

	public boolean verifyUser(String usernameOrEmail, String password);
}
