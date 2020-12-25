/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import com.antonioalejandro.smkt.users.dao.UserDao;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class UserService.
 */
@Slf4j
public class UserService implements IUserService {

	/** The repository. */
	@Autowired
	private UserDao repository;

	/** The role service. */
	@Autowired
	private RoleService roleService;

	/** The b crypt password encoder. */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	/** The default role id. */
	@Value("${default.params.roles.id}")
	private long defaultRoleId;

	/** The super admin id. */
	@Value(value = "${superadmin.id}")
	private long superAdminId;


	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@Override
	public UserResponse getUsers() {
		log.debug("Call to getUsers");
		List<User> users = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
		if (users.isEmpty()) {
			return new UserResponse(HttpStatus.NO_CONTENT, "No Content");
		}
		return new UserResponse(users);
	}

	/**
	 * Gets the user by email or username.
	 *
	 * @param value   the value
	 * @param isEmail the is email
	 * @return the user by email or username
	 */
	@Override
	public UserResponse getUserByEmailOrUsername(final String value, final boolean isEmail) {

		log.debug("Call to getUserByEmailOrUsername. Value:{}, IsEmail:{}", value, isEmail);

		final User user = isEmail ? repository.findByEmail(value) : repository.findByUsername(value);

		if (user == null) {
			return new UserResponse(HttpStatus.NOT_FOUND, isEmail ? "Email does't exists" : "Username does't exists");
		}

		return new UserResponse(user);
	}

	/**
	 * Gets the user by id.
	 *
	 * @param id the id
	 * @return the user by id
	 */
	@Override
	@Transactional(readOnly = true)
	public UserResponse getUserById(final long id) {
		log.debug("Call to getUserById");

		Optional<User> optUser = repository.findById(id);

		if (!optUser.isPresent()) {
			return new UserResponse(HttpStatus.NOT_FOUND, "Id not found. ");
		}

		return new UserResponse(optUser.get());
	}

	/**
	 * Update user.
	 *
	 * @param userUpdateRequest the user update request
	 * @param id                the id
	 * @return the user response
	 */
	@Override
	@Transactional
	public UserResponse updateUser(final UserUpdateRequest userUpdateRequest, final long id) {
		log.debug("Call to UpdateUser -> id: {}, user: {}", id, userUpdateRequest);
		final User currentUser = repository.findById(id).orElse(null);
		if (currentUser == null) {
			return new UserResponse(HttpStatus.BAD_REQUEST, "Id don't exists. ");
		}
		if (!updateEmail(currentUser, userUpdateRequest) || !updateUsername(currentUser, userUpdateRequest)
				|| !updateRole(currentUser, userUpdateRequest)) {
			return new UserResponse(HttpStatus.BAD_REQUEST,
					"Email , Username already exists or Role Name is not valid. ");
		}
		currentUser.setPassword(bCryptPasswordEncoder.encode(userUpdateRequest.getPassword()));
		currentUser.setName(userUpdateRequest.getName());
		currentUser.setLastname(userUpdateRequest.getLastname());
		return new UserResponse(repository.save(currentUser));
	}

	/**
	 * Delete user.
	 *
	 * @param id the id
	 * @return the user response
	 */
	@Override
	@Transactional
	public UserResponse deleteUser(final long id) {
		if (id == superAdminId) {
			return new UserResponse(HttpStatus.BAD_REQUEST, "Super Admin user can't be deleted. ");
		}
		if (repository.findById(id).isPresent()) {
			repository.deleteById(id);
			return new UserResponse(HttpStatus.ACCEPTED, "User was deleted");
		} else {
			return new UserResponse(HttpStatus.NOT_FOUND, "User doesn't exists");
		}
	}

	/**
	 * Creates the user.
	 *
	 * @param userRequest the user request
	 * @return the user response
	 */
	@Override
	@Transactional
	public UserResponse createUser(final UserRegistrationRequest userRequest) {
		log.debug("Call to Save");

		final Role role = roleService.getRoleById(defaultRoleId).getRole();

		if (existsEmail(userRequest.getEmail()) || existsUsername(userRequest.getUsername())) {
			return new UserResponse(HttpStatus.BAD_REQUEST, "BAD REQUEST");
		}

		final User newUser = new User();

		newUser.setName(userRequest.getName());
		newUser.setUsername(userRequest.getUsername());
		newUser.setEmail(userRequest.getEmail());
		newUser.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
		newUser.setRole(role);

		if (userRequest.getLastname() != null) {
			newUser.setLastname(userRequest.getLastname());
		}

		return new UserResponse(repository.save(newUser));
	}

	/**
	 * Exists email.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	private boolean existsEmail(String email) {
		return repository.getUsersSameEmail(email) == 1L;
	}

	/**
	 * Exists username.
	 *
	 * @param username the username
	 * @return true, if successful
	 */
	private boolean existsUsername(String username) {
		return repository.getUsersSameUsername(username) == 1L;
	}

	/**
	 * Update email.
	 *
	 * @param currentUser       the current user
	 * @param userUpdateRequest the user update request
	 * @return true, if successful
	 */
	private boolean updateEmail(User currentUser, UserUpdateRequest userUpdateRequest) {
		if (!currentUser.getEmail().equals(userUpdateRequest.getEmail())) {
			if (!existsEmail(userUpdateRequest.getEmail())) {
				currentUser.setEmail(userUpdateRequest.getEmail());
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Update username.
	 *
	 * @param currentUser       the current user
	 * @param userUpdateRequest the user update request
	 * @return true, if successful
	 */
	private boolean updateUsername(User currentUser, UserUpdateRequest userUpdateRequest) {
		if (!currentUser.getUsername().equals(userUpdateRequest.getUsername())) {
			if (!existsUsername(userUpdateRequest.getUsername())) {
				currentUser.setUsername(userUpdateRequest.getUsername());
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * Update role.
	 *
	 * @param currentUser       the current user
	 * @param userUpdateRequest the user update request
	 * @return true, if successful
	 */
	private boolean updateRole(User currentUser, UserUpdateRequest userUpdateRequest) {
		if (!currentUser.getRole().getName().equalsIgnoreCase(userUpdateRequest.getRole())) {
			RoleResponse roleAsParameter = roleService.getRoleByName(userUpdateRequest.getRole());
			if (!roleAsParameter.haveData()) {
				return false;
			} else {
				currentUser.setRole(roleAsParameter.getRole());
			}
		}
		return true;
	}

}
