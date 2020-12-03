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

import com.antonioalejandro.smkt.users.converter.RoleConverter;
import com.antonioalejandro.smkt.users.converter.UserConverter;
import com.antonioalejandro.smkt.users.dao.UserDao;
import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationDTO;
import com.antonioalejandro.smkt.users.pojo.UserResponse;
import com.antonioalejandro.smkt.users.pojo.UserUpdateRequest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserService implements IUserService {
	@Autowired
	private UserDao repository;
	@Autowired
	private RoleService roleService;
	@Autowired
	private RoleConverter roleConverter;
	@Autowired
	private UserConverter userConverter;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Value("${default.params.roles.id}")
	private static Long defaultRoleId;

	@Override
	public List<UserDTO> getUsers() {
		log.debug("Call to getUsers");
		return userConverter
				.convert(StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()));
	}

	@Override
	public List<UserDTO> getUsersByEmail(final String email) {

		log.debug("Call to getUsersByEmail");

		final String searchValue = email.toLowerCase();

		final List<User> list = StreamSupport.stream(repository.findAll().spliterator(), false)
				.collect(Collectors.toList());

		list.removeIf(x -> !x.getEmail().contains(searchValue)
				&& !(x.getName().toLowerCase() + " " + x.getLastname().toLowerCase()).contains(searchValue));

		return userConverter.convert(list);
	}

	@Override
	@Transactional(readOnly = true)
	public UserDTO getUserById(final long id) {
		log.debug("Call to getUserById");

		Optional<User> optUser = repository.findById(id);

		User user = optUser.isPresent() ? optUser.get() : null;

		return userConverter.convert(user);
	}

	@Override
	@Transactional
	public UserResponse updateUser(final UserUpdateRequest userUpdateRequest, final Long id) {
		log.debug("Call to UpdateUser -> id: {}, user: {}", id, userUpdateRequest);
		final Optional<User> oUser = repository.findById(id);
		if (oUser.isPresent()) {
			final User currentUser = oUser.get();
			if (!currentUser.getEmail().equals(userUpdateRequest.getEmail())) {
				if (repository.getUsersSameEmail(userUpdateRequest.getEmail()) == 0) {// valid
					currentUser.setEmail(userUpdateRequest.getEmail());
				} else {
					return new UserResponse(HttpStatus.BAD_REQUEST, "Email already exists");
				}
			}
			if (!currentUser.getUsername().equals(userUpdateRequest.getUsername())) {
				if (repository.getUsersSameUsername(userUpdateRequest.getUsername()) == 0) { // valid
					currentUser.setUsername(userUpdateRequest.getUsername());
				} else {
					return new UserResponse(HttpStatus.BAD_REQUEST, "Username already exists");
				}
			}
			currentUser.setPassword(bCryptPasswordEncoder.encode(userUpdateRequest.getPassword()));
			currentUser.setName(userUpdateRequest.getName());
			currentUser.setLastname(userUpdateRequest.getLastname());
			currentUser.setRole(roleConverter.apply(roleService.getRoleById(repository.getIdRoleByUserId(id))));

			return new UserResponse(HttpStatus.CREATED, "Updated", repository.save(currentUser));
		} else {
			return new UserResponse(HttpStatus.I_AM_A_TEAPOT, "User " + id + "don't exists");
		}
	}

	@Override
	@Transactional
	public UserResponse delete(final Long id) {
		if (repository.findById(id).isPresent()) {
			repository.deleteById(id);
			return new UserResponse(HttpStatus.ACCEPTED, "User was deleted");
		} else {
			return new UserResponse(HttpStatus.NOT_FOUND, "User doesn't exists");
		}
	}

	@Override
	@Transactional
	public UserDTO create(final UserRegistrationDTO user) {
		log.debug("Call to Save");
		
		if (repository.getUsersSameEmail(user.getEmail()) == 1
				|| repository.getUsersSameUsername(user.getUsername()) == 1 || user.getPassword() == null) {
			return null;
		}

		final Role role = roleConverter.apply(roleService.getRoleById(defaultRoleId));

		final User userToSave = new User();

		userToSave.setName(user.getName());
		userToSave.setLastname(user.getLastname());
		userToSave.setUsername(user.getUsername());
		userToSave.setEmail(user.getEmail());
		userToSave.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userToSave.setRole(role);

		final User userX = repository.save(userToSave);

		return userConverter.convert(userX);
	}

}
