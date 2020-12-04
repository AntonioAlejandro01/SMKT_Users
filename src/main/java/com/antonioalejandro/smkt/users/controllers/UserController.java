package com.antonioalejandro.smkt.users.controllers;

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

import com.antonioalejandro.smkt.users.pojo.BadRequestResponse;
import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationRequest;
import com.antonioalejandro.smkt.users.pojo.UserResponse;
import com.antonioalejandro.smkt.users.pojo.UserUpdateRequest;
import com.antonioalejandro.smkt.users.service.UserService;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Value("${scopes.read}")
	private String scopeRead;
	@Value("${scopes.write}")
	private String scopeWrite;
	@Value("${scopes.delete}")
	private String scopeDelete;

	private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern
			.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	private static final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");

	@Autowired
	private UserService userService;



	@GetMapping()
	public ResponseEntity<List<UserDTO>> getUsers(
			@RequestHeader(name = "Authorization", required = true) final String token) {

		log.info("Call users/all");
		if (!TokenUtils.isAuthorized(token, scopeRead)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		List<UserDTO> users = userService.getUsers();

		return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<?> searchUser(@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestParam(name = "username", required = false) final String username,
			@RequestParam(name = "email", required = false) final String email,
			@RequestParam(name = "id", required = false) final Long id) {

		if (!TokenUtils.isAuthorized(token, scopeRead)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		UserDTO user;
		String ms;
		if (username != null) {

			log.info("Call users/search?username={}", username);

			ms = validateUsername(username, false);
			if (!ms.isEmpty()) {
				return new ResponseEntity<>(new BadRequestResponse(ms, HttpStatus.BAD_REQUEST.toString()),
						HttpStatus.BAD_REQUEST);
			}
			user = userService.getUserByEmailOrUsername(username, false);

		} else if (email != null) {
			log.info("Call users/search?email={}", email);
			ms = validateEmail(email, false);
			if (!ms.isEmpty()) {
				return new ResponseEntity<>(new BadRequestResponse(ms, HttpStatus.BAD_REQUEST.toString()),
						HttpStatus.BAD_REQUEST);
			}
			user = userService.getUserByEmailOrUsername(email, true);

		} else if (id != null) {

			log.info("Call users/search?id={}", id);
			ms = validateId(id);
			if (!ms.isEmpty()) {
				return new ResponseEntity<>(new BadRequestResponse(ms, HttpStatus.BAD_REQUEST.toString()),
						HttpStatus.BAD_REQUEST);
			}
			user = userService.getUserById(id);
		} else {

			return new ResponseEntity<>(
					new BadRequestResponse("One of these params (username, email or id) can't be null",
							HttpStatus.BAD_REQUEST.toString()),
					HttpStatus.BAD_REQUEST);
		}

		return user != null ? new ResponseEntity<>(user, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@PostMapping()
	public ResponseEntity<?> create(@RequestHeader(name = "Authorization", required = true) final String token,
			final @RequestBody UserRegistrationRequest req) {

		log.info("Call users/create");

		if (!TokenUtils.isAuthorized(token, scopeWrite)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}

		log.debug("User -> {}", req.toString());

		StringBuilder badReqMs = new StringBuilder();

		badReqMs.append(validateEmail(req.getEmail(), true)).append(validateName(req.getName()))
				.append(validateUsername(req.getUsername(), true)).append(validatePassword(req.getPassword()))
				.append(validateLastname(req.getLastname()));

		if (!badReqMs.isEmpty()) {
			return new ResponseEntity<>(new BadRequestResponse(badReqMs.toString(), HttpStatus.BAD_REQUEST.toString()),
					HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(userService.create(req), HttpStatus.CREATED);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteUser(@RequestHeader(name = "Authorization", required = true) final String token,
			@PathVariable final Long id) {

		log.info("Call users/delete/{}", id);

		if (!TokenUtils.isAuthorized(token, scopeDelete)) {
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		String ms = validateId(id);
		if (!ms.isEmpty()) {
			return new ResponseEntity<>(new BadRequestResponse(ms, HttpStatus.BAD_REQUEST.toString()),
					HttpStatus.BAD_REQUEST);
		}
		final UserResponse userResponse = userService.delete(id);

		return new ResponseEntity<>(userResponse, userResponse.getStatus());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> putUserById(@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestBody final UserUpdateRequest req, @PathVariable("id") final Long id) {

		log.info("Call users/id");

		if (!TokenUtils.isAuthorized(token, scopeWrite)) {
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

	private String validateEmail(String email, boolean search) {
		if (email.isBlank()) {
			return "Email is mandatory. ";
		} else {
			if (!validateEmailRegex(email)) {
				return "Email is not valid. ";
			} else {
				if (search && userService.existsEmail(email)) {
					return "Email exists. ";
				}
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

	private String validateUsername(String username, boolean search) {

		if (username.isBlank()) {
			return "username is mandatory. ";
		} else {
			if (username.length() < 5) {
				return "Username minimun length is 5. ";
			} else {
				if (search && userService.existsUsername(username)) {
					return "Username exists. ";
				}
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
		if (!lastname.isBlank() && lastname.length() < 3) {
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
