/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.service;

import java.util.Arrays;
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
import com.antonioalejandro.smkt.users.pojo.TokenData;
import com.antonioalejandro.smkt.users.pojo.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;
import com.antonioalejandro.smkt.users.utils.Constants;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class UserService.
 */
@Slf4j
public class UserService implements IUserService {

	/** The default role id. */
	@Value("${default.params.roles.id}")
	private long defaultRoleId;

	/** The super admin id. */
	@Value(value = "${superadmin.id}")
	private long superAdminId;

	@Value("${scopes.super}")
	private String scopeSuper;

	@Value("${scopes.adm}")
	private String scopeAdm;

	@Value("${scopes.read-min}")
	private String scopeReadMin;

	@Value("${scopes.update-self}")
	private String scopeUpdateSelf;

	/** The repository. */
	@Autowired
	private UserDao repository;

	/** The role service. */
	@Autowired
	private RoleService roleService;

	/** The b crypt password encoder. */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private TokenUtils tokenUtils;

	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	@Override
	public UserResponse getUsers(final TokenData tokenData) {
		log.debug("Call to getUsers");
		List<User> users = StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
		if (users.isEmpty()) {
			return new UserResponse(HttpStatus.NO_CONTENT, "No Content");
		}

		return adaptUsersByScopes(users, tokenData);
	}

	/**
	 * Gets the user by email or username.
	 *
	 * @param value   the value
	 * @param isEmail the is email
	 * @return the user by email or username
	 */
	@Override
	public UserResponse getUserByEmailOrUsername(final String value, final boolean isEmail, final TokenData tokenData) {

		log.debug("Call to getUserByEmailOrUsername. Value:{}, IsEmail:{}", value, isEmail);

		final User user = isEmail ? repository.findByEmail(value) : repository.findByUsername(value);

		if (user == null) {
			return new UserResponse(HttpStatus.NOT_FOUND, isEmail ? "Email does't exists" : "Username does't exists");
		}

		return adaptUserByScopes(user, tokenData);
	}

	@Override
	public UserResponse getUserByUsernameKey(final String value) {

		log.debug("Call to getUserByEmailOrUsername. Value:{}", value);

		final User user = repository.findByUsername(value);

		if (user == null) {
			return new UserResponse(HttpStatus.NOT_FOUND, "Username does't exists");
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
	public UserResponse getUserById(final long id, final TokenData tokenData) {
		log.debug("Call to getUserById");

		Optional<User> optUser = repository.findById(id);

		if (!optUser.isPresent()) {
			return new UserResponse(HttpStatus.NOT_FOUND, "Id not found. ");
		}

		return adaptUserByScopes(optUser.get(), tokenData);
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
	public UserResponse updateUser(final UserUpdateRequest userUpdateRequest, final long id,
			final TokenData tokenData) {
		log.debug("Call to UpdateUser -> id: {}, user: {}", id, userUpdateRequest);
		final User currentUser = repository.findById(id).orElse(null);
		if (currentUser == null) {
			return new UserResponse(HttpStatus.BAD_REQUEST, "Id don't exists. ");
		}
		Optional<UserResponse> oUserResponse = canUpdate(tokenData, currentUser, userUpdateRequest);

		if (oUserResponse.isPresent()) {
			return oUserResponse.get();
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
	public UserResponse deleteUser(final long id, final TokenData tokenData) {
		if (id == superAdminId) {
			return new UserResponse(HttpStatus.BAD_REQUEST, "Super Admin user can't be deleted. ");
		}
		boolean canDelete = false;
		if(tokenUtils.isAuthorized(Arrays.asList(scopeSuper),tokenData)) {
			canDelete = true;
		} else if (tokenUtils.isAuthorized(Arrays.asList(scopeAdm), tokenData)) {
			Optional<User> objectiveUser = repository.findById(id);
			if (objectiveUser.isPresent() && Constants.USER_ROLE_NAME.equals(objectiveUser.get().getRole().getName())) {
				canDelete = true;
			}
		}

		if (canDelete && repository.findById(id).isPresent()) {
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
	public UserResponse createUser(final UserRegistrationRequest userRequest, final TokenData tokenData) {
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

	private UserResponse adaptUsersByScopes(List<User> users, TokenData tokenData) {

		if (tokenUtils.isAuthorized(Arrays.asList(scopeReadMin), tokenData)) {
			users = users.stream().map(user -> {
				User newUser = new User();
				newUser.setUsername(user.getUsername());
				return newUser;
			}).collect(Collectors.toList());
		} else if (tokenUtils.isAuthorized(Arrays.asList(scopeAdm), tokenData)) {
			users = users.stream().map(user -> {
				user.setId(null);
				if (Constants.SUPERADMIN_ROLE_NAME.equals(user.getRole().getName()) || Constants.ADMIN_ROLE_NAME.equals(user.getRole().getName())) {
					user.setPassword(null);
				}
				return user;
			}).collect(Collectors.toList());
		} else if (!tokenUtils.isAuthorized(Arrays.asList(scopeSuper), tokenData)) {
			return new UserResponse(HttpStatus.UNAUTHORIZED, "You haven't got the correct scope");
		}

		return new UserResponse(users);
	}

	private UserResponse adaptUserByScopes(User user, TokenData tokenData) {
		if (tokenUtils.isAuthorized(Arrays.asList(scopeReadMin), tokenData)) {
			String username = user.getUsername();
			user = new User();
			user.setUsername(username);
		} else if (tokenUtils.isAuthorized(Arrays.asList(scopeAdm), tokenData)) {
			user.setId(null);
			if (Constants.SUPERADMIN_ROLE_NAME.equals(user.getRole().getName()) || Constants.ADMIN_ROLE_NAME.equals(user.getRole().getName())) {
				user.setPassword(null);
			}

		} else if (!tokenUtils.isAuthorized(Arrays.asList(scopeSuper), tokenData)) {
			return new UserResponse(HttpStatus.UNAUTHORIZED, "You haven't got the correct scope");
		}
		return new UserResponse(user);
	}

	private Optional<UserResponse> canUpdate(TokenData tokenData, User currentUser,
			UserUpdateRequest userUpdateRequest) {

		if (tokenUtils.isAuthorized(Arrays.asList(scopeUpdateSelf), tokenData)) {
			return doIfUpdateYourself(currentUser, userUpdateRequest, tokenData);
		} else if (tokenUtils.isAuthorized(Arrays.asList(scopeAdm), tokenData)) {
			if (currentUser.getUsername().equals(tokenData.getUsername())) {
				userUpdateRequest.setRole(Constants.ADMIN_ROLE_NAME);
				return Optional.empty();
			} else {
				if (Constants.USER_ROLE_NAME.equals(currentUser.getRole().getName())) {
					userUpdateRequest.setRole(Constants.USER_ROLE_NAME);
					return Optional.empty();
				} else {
					return Optional.of(new UserResponse(HttpStatus.BAD_REQUEST,
							"You can't update ADMIN users or SUPERADMIN user"));
				}
			}
		} else if (tokenUtils.isAuthorized(Arrays.asList(scopeSuper), tokenData)) {
			if (currentUser.getUsername().equals(tokenData.getUsername())) {
				userUpdateRequest.setRole(Constants.SUPERADMIN_ROLE_NAME);
				return Optional.empty();
			}
			if (Constants.SUPERADMIN_ROLE_NAME.equals(userUpdateRequest.getRole())) {
				return Optional.of(new UserResponse(HttpStatus.BAD_REQUEST, "You can't update to SUPERADMIN user"));
			}
			return Optional.empty();
		} else {
			return Optional.of(new UserResponse(HttpStatus.UNAUTHORIZED, "You can't update users"));
		}
	}
	
	private Optional<UserResponse> doIfUpdateYourself(User currentUser,UserUpdateRequest userUpdateRequest, TokenData tokenData) {
		if (currentUser.getUsername().equals(tokenData.getUsername())) {
			userUpdateRequest.setRole(Constants.USER_ROLE_NAME);
			return Optional.empty();
		} else {
			return Optional.of(new UserResponse(HttpStatus.BAD_REQUEST, "You only can update yourself"));
		}
	}

}
