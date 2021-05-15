/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.antonioalejandro.smkt.users.config.AppEnviroment;
import com.antonioalejandro.smkt.users.model.TokenData;
import com.antonioalejandro.smkt.users.model.request.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.model.request.UserUpdateRequest;
import com.antonioalejandro.smkt.users.model.response.GenericResponse;
import com.antonioalejandro.smkt.users.model.response.UserResponse;
import com.antonioalejandro.smkt.users.service.impl.UserServiceImpl;
import com.antonioalejandro.smkt.users.utils.Constants;
import com.antonioalejandro.smkt.users.utils.TokenUtils;
import com.antonioalejandro.smkt.users.utils.Validations;

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

	/** The user service. */
	@Autowired
	private UserServiceImpl userService;

	/** The token utils. */
	@Autowired
	private TokenUtils tokenUtils;

	/** The env. */
	@Autowired
	private AppEnviroment env;

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
	 * @param token the token
	 * @param appKey the app key
	 * @param filter the filter
	 * @param value the value
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

		if (!Validations.isFieldPresent(appKey)) {

			if (Validations.validateNullFields(filter, value)) {
				return createBadRequestException("The filter and value are mandatory. ");
			}

			return doIfNotAppKey(filter, value, tokenUtils.getDataToken(token));
		}

		return doIfAppkey(appKey, value);

	}

	/**
	 * Creates the.
	 *
	 * @param token the token
	 * @param req the req
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

		ms.append(Validations.validateEmail(req.getEmail())).append(Validations.validateName(req.getName()))
				.append(Validations.validateUsername(req.getUsername()))
				.append(Validations.validatePassword(req.getPassword()))
				.append(Validations.validateLastname(req.getLastname()));

		if (!ms.toString().isEmpty()) {
			return createBadRequestException(ms.toString());
		}

		return prepareResponse(userService.createUser(req, tokenUtils.getDataToken(token)), HttpStatus.CREATED);
	}

	/**
	 * Delete user.
	 *
	 * @param token the token
	 * @param id the id
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

		String ms = Validations.validateId(id, true);
		if (!ms.isEmpty()) {
			return createBadRequestException(ms);
		}

		return prepareResponse(userService.deleteUser(id, tokenUtils.getDataToken(token)), HttpStatus.ACCEPTED);
	}

	/**
	 * Put user by id.
	 *
	 * @param token the token
	 * @param req the req
	 * @param id the id
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

		String ms = Validations.validateId(id, true);
		
		if (!ms.isEmpty()) {
			return createBadRequestException(ms);
		}

		return prepareResponse(userService.updateUser(req, id, tokenUtils.getDataToken(token)), HttpStatus.ACCEPTED);
	}

	/**
	 * Creates the unathorized response.
	 *
	 * @param ms the ms
	 * @return the response entity
	 */
	private ResponseEntity<UserResponse> createUnathorizedResponse(String ms) {
		return new ResponseEntity<>(
				new UserResponse(HttpStatus.UNAUTHORIZED, !Validations.isFieldPresent(ms) ? HttpStatus.UNAUTHORIZED.getReasonPhrase() : ms),
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
				new UserResponse(HttpStatus.BAD_REQUEST, !Validations.isFieldPresent(ms) ? HttpStatus.BAD_REQUEST.getReasonPhrase() : ms),
				HttpStatus.BAD_REQUEST);
	}

	/**
	 * Prepare response.
	 *
	 * @param userResponse the user response
	 * @param okOption the ok option
	 * @return the response entity
	 */
	private ResponseEntity<UserResponse> prepareResponse(UserResponse userResponse, HttpStatus okOption) {
		return new ResponseEntity<>(userResponse, userResponse.haveData() ? okOption : userResponse.getHttpStatus());
	}

	/**
	 * Do if id filter.
	 *
	 * @param value the value
	 * @return the optional
	 */
	private Optional<ResponseEntity<UserResponse>> doIfIdFilter(String value) {
		long id;
		try {
			id = Long.parseLong(value);
		} catch (Exception e) {
			return Optional.of(createBadRequestException(Constants.ID_MUST_BE_A_NUMBER));
		}
		String ms = Validations.validateId(id, true);
		if (!ms.isEmpty()) {
			return Optional.of(createBadRequestException(ms));
		}
		return Optional.empty();
	}

	/**
	 * Do if appkey.
	 *
	 * @param appKey the app key
	 * @param value the value
	 * @return the response entity
	 */
	private ResponseEntity<UserResponse> doIfAppkey(String appKey, String value) {
		if (Validations.validateAppKey(appKey, env.getAppKeySecret())) {
			return createUnathorizedResponse(Constants.APPKEY_NOT_VALID);
		} else {
			String ms = Validations.validateUsername(value);
			if (!ms.isEmpty()) {
				return createBadRequestException(ms);
			}
			return prepareResponse(userService.getUserByUsernameKey(value), HttpStatus.OK);
		}

	}

	/**
	 * Do if not app key.
	 *
	 * @param filter the filter
	 * @param value the value
	 * @param tokenData the token data
	 * @return the response entity
	 */
	private ResponseEntity<UserResponse> doIfNotAppKey(String filter, String value, TokenData tokenData) {
		if (filter.equalsIgnoreCase(Constants.FILTER_ID)) {
			Optional<ResponseEntity<UserResponse>> oUserResponse = doIfIdFilter(value);
			if (oUserResponse.isPresent()) {
				return oUserResponse.get();
			}
			return prepareResponse(userService.getUserById(Long.parseLong(value), tokenData), HttpStatus.OK);
		}
		if (filter.equalsIgnoreCase(Constants.FILTER_USERNAME) || filter.equalsIgnoreCase(Constants.FILTER_EMAIL)) {
			boolean isUsername = filter.equalsIgnoreCase(Constants.FILTER_USERNAME);
			String ms = isUsername ? Validations.validateUsername(value) : Validations.validateEmail(value);
			if (!ms.isEmpty()) {
				return createBadRequestException(ms);
			}
			return prepareResponse(userService.getUserByEmailOrUsername(value, !isUsername, tokenData), HttpStatus.OK);
		}
		return createBadRequestException(Constants.FILTER_NOT_VALID);
	}

}
