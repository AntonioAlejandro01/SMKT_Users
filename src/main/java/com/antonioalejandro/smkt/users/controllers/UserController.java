/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.pojo.TokenData;
import com.antonioalejandro.smkt.users.pojo.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.pojo.response.GenericResponse;
import com.antonioalejandro.smkt.users.pojo.response.UserResponse;
import com.antonioalejandro.smkt.users.service.UserService;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class UserController.
 */
@RestController
@Slf4j
@RequestMapping("/users")
@Api(value = "/users", tags = { "Users" }, produces = "application/json", consumes = "application/json")
public class UserController {

	@Value("${oauth.app-key-secret}")
	private String secretApp;

	/** The Constant VALID_EMAIL_ADDRESS_REGEX. */
	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	/** The Constant VALID_PASSWORD_REGEX. */
	private static final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");

	/** The user service. */
	@Autowired
	private UserService userService;

	@Autowired
	private TokenUtils tokenUtils;

	/**
	 * Gets the users.
	 *
	 * @param token the token
	 * @return the users
	 */
	@ApiOperation(value = "Get all users", produces = "application/json", response = UserResponse.class, tags = "Users", authorizations = {
			@Authorization(value = "GetUsers", scopes = {
					@AuthorizationScope(scope = "users.super", description = "Can see all data for all users, be admin or not"),
					@AuthorizationScope(scope = "users.admin", description = "Only can see all data for normal users"),
					@AuthorizationScope(scope = "users.read-min", description = "Only can see a usernames of all users"), }), })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = UserResponse.class),
			@ApiResponse(code = 204, message = "No Content", response = GenericResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = GenericResponse.class) })
	@GetMapping()
	public ResponseEntity<UserResponse> getUsers(
			@RequestHeader(name = "Authorization", required = true) final String token) {

		log.info("Call users/all");
		return prepareResponse(userService.getUsers(tokenUtils.getDataToken(token)), HttpStatus.OK);

	}

	/**
	 * Search user.
	 *
	 * @param token  the token
	 * @param filter the filter
	 * @param value  the value
	 * @return the response entity
	 */
	@ApiOperation(value = "Saerch user by username, email or id ", produces = "application/json", response = UserResponse.class, tags = "Users", authorizations = {
			@Authorization(value = "SearchUsers", scopes = {
					@AuthorizationScope(scope = "users.super", description = "Can see all data for all users, be admin or not"),
					@AuthorizationScope(scope = "users.admin", description = "Only can see all data for normal users"),
					@AuthorizationScope(scope = "users.read-min", description = "Only can see a usernames of all users and only can filter by username"), }), })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = UserResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = UserResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = UserResponse.class) })
	@GetMapping("/search")
	public ResponseEntity<UserResponse> searchUser(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestHeader(name = "App-Key", required = false) final String appKey,
			@RequestParam(name = "filter", required = true) final String filter,
			@RequestParam(name = "value", required = true) final String value) {

		if (appKey == null || appKey.isBlank()) {

			if (validateNullFields(filter, value)) {
				return createBadRequestException("The filter and value are mandatory. ");
			}

			TokenData tokenData = tokenUtils.getDataToken(token);

			UserResponse userResponse;

			if (filter.equalsIgnoreCase("id")) {
				long id;
				try {
					id = Long.parseLong(value);
				} catch (Exception e) {
					return createBadRequestException("The id must be a number");
				}
				String ms = validateId(id);
				if (!ms.isEmpty()) {
					return createBadRequestException(ms);
				}
				userResponse = userService.getUserById(id, tokenData);
			} else if (filter.equalsIgnoreCase("username") || filter.equalsIgnoreCase("email")) {
				boolean isUsername = filter.equalsIgnoreCase("username");
				String ms = isUsername ? validateUsername(value) : validateEmail(value);
				if (!ms.isEmpty()) {
					return createBadRequestException(ms);
				}
				userResponse = userService.getUserByEmailOrUsername(value, !isUsername, tokenData);
			} else {
				return createBadRequestException("Filter type is not valid. (id, username or email). ");
			}

			return prepareResponse(userResponse, HttpStatus.OK);
		}

		if (validateAppKey(appKey)) {
			return createUnathorizedResponse("The AppKey is not valid");
		} else {
			String ms = validateUsername(value);
			if (!ms.isEmpty()) {
				return createBadRequestException(ms);
			}
			return prepareResponse(userService.getUserByUsernameKey(value), HttpStatus.OK);
		}

	}

	/**
	 * Creates the.
	 *
	 * @param token the token
	 * @param req   the req
	 * @return the response entity
	 */
	@ApiOperation(value = "Create a user ", produces = "application/json", response = UserResponse.class, tags = "Users", authorizations = {
			@Authorization(value = "Create", scopes = {
					@AuthorizationScope(scope = "users.super", description = "Can create Admins and normal users"),
					@AuthorizationScope(scope = "users.admin", description = "Only can create normal users"), }), })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = UserResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = UserResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = UserResponse.class) })
	@PostMapping()
	public ResponseEntity<UserResponse> create(
			@RequestHeader(name = "Authorization", required = true) final String token,
			final @RequestBody(required = true) UserRegistrationRequest req) {

		log.info("Call users/create");

		log.debug("User -> {}", req.toString());

		StringBuilder ms = new StringBuilder();

		ms.append(validateEmail(req.getEmail())).append(validateName(req.getName()))
				.append(validateUsername(req.getUsername())).append(validatePassword(req.getPassword()))
				.append(validateLastname(req.getLastname()));

		if (!ms.toString().isEmpty()) {
			return createBadRequestException(ms.toString());
		}

		return prepareResponse(userService.createUser(req, tokenUtils.getDataToken(token)), HttpStatus.CREATED);
	}

	/**
	 * Delete user.
	 *
	 * @param token the token
	 * @param id    the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Delete user by id ", produces = "application/json", response = UserResponse.class, tags = "Users", authorizations = {
			@Authorization(value = "DeleteUser", scopes = {
					@AuthorizationScope(scope = "users.super", description = "Can delete admins and normal users"),
					@AuthorizationScope(scope = "users.admin", description = "Only can delete normal users") }), })
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted", response = UserResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = UserResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = UserResponse.class) })
	@DeleteMapping("/{id}")
	public ResponseEntity<UserResponse> deleteUser(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@PathVariable(name = "id", required = true) final Long id) {

		log.info("Call users/delete/{}", id);

		String ms = validateId(id);
		if (!ms.isEmpty()) {
			return createBadRequestException(ms);
		}

		return prepareResponse(userService.deleteUser(id, tokenUtils.getDataToken(token)), HttpStatus.ACCEPTED);
	}

	/**
	 * Put user by id.
	 *
	 * @param token the token
	 * @param req   the req
	 * @param id    the id
	 * @return the response entity
	 */
	@ApiOperation(value = "Update user by id", produces = "application/json", response = UserResponse.class, tags = "Users", authorizations = {
			@Authorization(value = "putUser", scopes = {
					@AuthorizationScope(scope = "users.super", description = "Can update admins and normal users"),
					@AuthorizationScope(scope = "users.admin", description = "Only can update normal users"),
					@AuthorizationScope(scope = "users.update-self", description = "Only can update hisself") }), })
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted", response = UserResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = UserResponse.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = UserResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = UserResponse.class) })
	@PutMapping("/{id}")
	public ResponseEntity<UserResponse> putUserById(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestBody(required = true) final UserUpdateRequest req,
			@PathVariable(value = "id", required = true) final Long id) {

		log.info("Call users/id");

		String ms = validateId(id);
		if (!ms.isEmpty()) {
			return createBadRequestException(ms);
		}

		return prepareResponse(userService.updateUser(req, id, tokenUtils.getDataToken(token)), HttpStatus.ACCEPTED);
	}

	/**
	 * Validate email.
	 *
	 * @param email the email
	 * @return the string
	 */
	private String validateEmail(String email) {
		if (email.isBlank()) {
			return "Email is mandatory. ";
		} else {
			if (!validateEmailRegex(email)) {
				return "Email is not valid. ";
			}
		}
		return "";

	}

	/**
	 * Validate email regex.
	 *
	 * @param email the email
	 * @return true, if successful
	 */
	private boolean validateEmailRegex(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

	/**
	 * Validate name.
	 *
	 * @param name the name
	 * @return the string
	 */
	private String validateName(String name) {
		if (name.isBlank()) {
			return "Name is mandatory. ";
		} else {
			if (name.length() < 3) {
				return "Name minimun length is 3. ";
			}
		}
		return "";
	}

	/**
	 * Validate username.
	 *
	 * @param username the username
	 * @return the string
	 */
	private String validateUsername(String username) {

		if (username.isBlank()) {
			return "username is mandatory. ";
		} else {
			if (username.length() < 5) {
				return "Username minimun length is 5. ";
			}
		}

		return "";
	}

	/**
	 * Validate password.
	 *
	 * @param password the password
	 * @return the string
	 */
	private String validatePassword(String password) {
		if (password.isBlank()) {
			return "Password is mandatory. ";
		} else {
			if (!validatePasswordRegex(password)) {
				return "Password is not valid. " + "The password minimun requirements are: "
						+ "one number, one upper case ," + " one lower case,"
						+ " one special character ( !, @, #, (, &, ) ) and length 8~20. ";
			}
		}
		return "";
	}

	/**
	 * Validate password regex.
	 *
	 * @param password the password
	 * @return true, if successful
	 */
	private boolean validatePasswordRegex(String password) {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}

	/**
	 * Validate lastname.
	 *
	 * @param lastname the lastname
	 * @return the string
	 */
	private String validateLastname(String lastname) {
		if (lastname != null && !lastname.isBlank() && lastname.length() < 3) {
			return "Lastname minimun length is 3. ";
		}
		return "";
	}

	/**
	 * Validate id.
	 *
	 * @param id the id
	 * @return the string
	 */
	private String validateId(Long id) {
		if (id < 1) {
			return "id can't be less or equal than zero. ";
		}
		return "";
	}

	/**
	 * Creates the unathorized response.
	 *
	 * @param ms the ms
	 * @return the response entity
	 */
	private ResponseEntity<UserResponse> createUnathorizedResponse(String ms) {
		return new ResponseEntity<>(
				new UserResponse(HttpStatus.UNAUTHORIZED, ms == null || ms.isBlank() ? "Unauthorized" : ms),
				HttpStatus.UNAUTHORIZED);
	}

	/**
	 * Creates the bad request exception.
	 *
	 * @param ms the ms
	 * @return the response entity
	 */
	private ResponseEntity<UserResponse> createBadRequestException(String ms) {
		return new ResponseEntity<>(
				new UserResponse(HttpStatus.BAD_REQUEST, ms == null || ms.isBlank() ? "Bad Request" : ms),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Prepare response.
	 *
	 * @param userResponse the user response
	 * @param okOption     the ok option
	 * @return the response entity
	 */
	private ResponseEntity<UserResponse> prepareResponse(UserResponse userResponse, HttpStatus okOption) {
		return new ResponseEntity<>(userResponse, userResponse.haveData() ? okOption : userResponse.getHttpStatus());
	}

	private boolean validateAppKey(String appKey) {
		return appKey != null && !secretApp.equals(DigestUtils.sha256Hex(appKey));
	}

	private boolean validateNullFields(String filter, String value) {
		return filter == null || value == null;

	}

}
