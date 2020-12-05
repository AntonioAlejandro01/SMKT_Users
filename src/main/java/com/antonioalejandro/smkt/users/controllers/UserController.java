package com.antonioalejandro.smkt.users.controllers;

import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import com.antonioalejandro.smkt.users.exceptions.BadRequestException;
import com.antonioalejandro.smkt.users.exceptions.BadRequestResponse;
import com.antonioalejandro.smkt.users.exceptions.NotFoundException;
import com.antonioalejandro.smkt.users.exceptions.RequestException;
import com.antonioalejandro.smkt.users.exceptions.UnauthorizedException;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;
import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.UserResponse;
import com.antonioalejandro.smkt.users.pojo.UserUpdateRequest;
import com.antonioalejandro.smkt.users.service.UserService;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Value("${scopes.read}")
	private String scopeRead;

	@Value("${scopes.read-min}")
	private String scopeReadMin;

	@Value("${scopes.write}")
	private String scopeWrite;

	@Value("${scopes.delete}")
	private String scopeDelete;

	@Value("${scopes.update}")
	private String scopeUpdate;

	@Value("${scopes.update-self}")
	private String scopeUpdateSelf;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");

	@Autowired
	private UserService userService;

	@GetMapping()
	public List<UserDTO> getUsers(@RequestHeader(name = "Authorization", required = true) final String token) {

		log.info("Call users/all");
		if (!TokenUtils.isAuthorized(token, scopeRead)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		List<UserDTO> users = userService.getUsers();

		return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(users, HttpStatus.OK);
	}

	@ApiOperation(value = "Saerch user by username, email or id ", response = UserDTO.class, tags = "searchUser")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = UserDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedException.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Exception.class) })
	@GetMapping("/search")
	public UserDTO searchUser(@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestParam(name = "filter", required = false) final String filter,
			@RequestParam(name = "value", required = false) final String value) throws RequestException {

		if (!TokenUtils.isAuthorized(token, scopeRead)) {
			throw new UnauthorizedException();
		}

		UserDTO user;
		StringBuilder ms = new StringBuilder();
		if (filter == null || value == null) {
			throw new BadRequestException("The filter and value are mandatory. ");
		}

		if (filter.equalsIgnoreCase("id")) {
			long id;
			try {
				id = Long.parseLong(value);
			} catch (Exception e) {
				throw new BadRequestException("The id must be a number");
			}
			ms.append(validateId(id));
			if (!ms.isEmpty()) {
				throw new BadRequestException(ms.toString());
			}
			user = userService.getUserById(id);
		} else if (filter.equalsIgnoreCase("username") || filter.equalsIgnoreCase("email")) {
			boolean isUsername = filter.equalsIgnoreCase("username");
			ms.append(isUsername ? validateUsername(value) : validateEmail(value));
			if (!ms.isEmpty()) {
				throw new BadRequestException(ms.toString());
			}
			user = userService.getUserByEmailOrUsername(value, !isUsername);
		} else {

			throw new BadRequestException("filter typoe is not valid. (id, username or email). ");
		}

		return user;

	}

	@ApiOperation(value = "Saerch user by username, email or id ", response = UserDTO.class, tags = "searchUser")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = UserDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedException.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Exception.class) })
	@PostMapping()
	public UserDTO create(@RequestHeader(name = "Authorization", required = true) final String token,
			final @RequestBody UserRegistrationRequest req) throws RequestException {

		log.info("Call users/create");

		if (!TokenUtils.isAuthorized(token, scopeWrite)) {
			throw new UnauthorizedException();
		}

		log.debug("User -> {}", req.toString());

		StringBuilder badReqMs = new StringBuilder();

		badReqMs.append(validateEmail(req.getEmail())).append(validateName(req.getName()))
				.append(validateUsername(req.getUsername())).append(validatePassword(req.getPassword()))
				.append(validateLastname(req.getLastname()));

		if (!badReqMs.toString().isEmpty()) {
			throw new BadRequestException(badReqMs.toString());
		}

		return userService.create(req);
	}
	@ApiOperation(value = "Saerch user by username, email or id ", response = UserResponse.class, tags = "searchUser")
	@ApiResponses(value = { @ApiResponse(code = 202, message = "Accepted", response = UserResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 401, message = "Unauthorized", response = UnauthorizedException.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Exception.class) })
	@DeleteMapping("/{id}")
	public UserResponse deleteUser(@RequestHeader(name = "Authorization", required = true) final String token,
			@PathVariable final Long id) throws RequestException{

		log.info("Call users/delete/{}", id);

		if (!TokenUtils.isAuthorized(token, scopeDelete)) {
			throw new UnauthorizedException();
		}
		String ms = validateId(id);
		if (!ms.isEmpty()) {
			throw new BadRequestException(ms);
		}

		return userService.delete(id);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> putUserById(@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestBody final UserUpdateRequest req, @PathVariable("id") final Long id) {

		log.info("Call users/id");

		if (!TokenUtils.isAuthorized(token, scopeUpdate) || (TokenUtils.isAuthorized(token, scopeUpdateSelf)
				&& !TokenUtils.getDataToken(token).getId().toString().equals(id.toString()))) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		String ms = validateId(id);
		if (!ms.isEmpty()) {
			return new ResponseEntity<>(new BadRequestResponse(ms, HttpStatus.BAD_REQUEST.toString()),
					HttpStatus.BAD_REQUEST);
		}

		final UserResponse userResponse = userService.updateUser(req, id);

		return userResponse.getUser() == null ? new ResponseEntity<>(userResponse.getStatus())
				: new ResponseEntity<>(userResponse, userResponse.getStatus());
	}

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

	private boolean validateEmailRegex(String email) {
		Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
		return matcher.find();
	}

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

	private boolean validatePasswordRegex(String password) {
		Matcher matcher = VALID_PASSWORD_REGEX.matcher(password);
		return matcher.find();
	}

	private String validateLastname(String lastname) {
		if (lastname != null && !lastname.isBlank() && lastname.length() < 3) {
			return "Lastname minimun length is 3. ";
		}
		return "";
	}

	private String validateId(Long id) {
		if (id < 1) {
			return "id can't be less or equal than zero. ";
		}
		return "";
	}

}
