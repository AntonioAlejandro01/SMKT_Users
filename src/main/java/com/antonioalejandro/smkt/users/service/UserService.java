package com.antonioalejandro.smkt.users.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
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

	private static final Logger log = Logger.getLogger(UserService.class.getName());

	private static final Long DEFAULTROLEID = 2l;

	@Override
	public List<UserDTO> getUsers() {
		log.log(Level.INFO, "Call to getUsers");
		return userConverter
				.convert(StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList()));
	}

	@Override
	public List<UserDTO> getUsersByEmail(final String email) {
		log.log(Level.INFO, "Call to getUsersByEmail");
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
		log.log(Level.INFO, "Call to getUserById");
		Optional<User> optUser = repository.findById(id);
		User user = optUser.isPresent() ? optUser.get() : null;
		return userConverter.convert(user);
	}

	@Override
	@Transactional
	public UserResponse updateUser(final UserRegistrationDTO userRegistrationDTO, final Long id) {
		log.log(Level.INFO, "Call to UpdateUser");
		final Optional<User> oUser = repository.findById(id);
		if (oUser.isPresent()) {
			final User currentUser = oUser.get();
			if (!currentUser.getEmail().equals(userRegistrationDTO.getEmail())) {
				if (repository.getUsersSameEmail(userRegistrationDTO.getEmail()) == 0) {// valid
					currentUser.setEmail(userRegistrationDTO.getEmail());
				} else {
					return new UserResponse(HttpStatus.BAD_REQUEST, "Email already exists");
				}
			}
			if (!currentUser.getUsername().equals(userRegistrationDTO.getUsername())) {
				if (repository.getUsersSameUsername(userRegistrationDTO.getUsername()) == 0) { // valid
					currentUser.setUsername(userRegistrationDTO.getUsername());
				} else {
					return new UserResponse(HttpStatus.BAD_REQUEST, "Username already exists");
				}
			}
			currentUser.setPassword(bCryptPasswordEncoder.encode(userRegistrationDTO.getPassword()));
			currentUser.setName(userRegistrationDTO.getName());
			currentUser.setLastname(userRegistrationDTO.getLastname());
			currentUser.setRole(roleConverter.apply(roleService.getRoleById(repository.getIdRoleByUserId(id))));

			return new UserResponse(HttpStatus.OK, "", repository.save(currentUser));
		} else {
			return new UserResponse(HttpStatus.I_AM_A_TEAPOT, "User " + id + "don't exists");
		}
	}

	@Override
	@Transactional
	public UserResponse delete(final Long id) {
		if (repository.findById(id).isPresent()) {
			repository.deleteById(id);
			return new UserResponse(HttpStatus.OK, "User was deleted");
		} else {
			return new UserResponse(HttpStatus.OK, "User doesnt exists");
		}
	}

	@Override
	@Transactional
	public UserDTO create(final UserRegistrationDTO user) {
		log.log(Level.INFO, "Call to Save");
		if (repository.getUsersSameEmail(user.getEmail()) == 1) {
			return null;
		}
		if (repository.getUsersSameUsername(user.getUsername()) == 1) {
			return null;
		}
		if (user.getPassword() == null) {
			return null;
		}

		final Role role = roleConverter.apply(roleService.getRoleById(DEFAULTROLEID));

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
