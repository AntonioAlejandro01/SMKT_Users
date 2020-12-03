package com.antonioalejandro.smkt.users.controllers;

import java.util.List;
import java.util.Optional;

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

import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationDTO;
import com.antonioalejandro.smkt.users.pojo.UserResponse;
import com.antonioalejandro.smkt.users.pojo.UserUpdateRequest;
import com.antonioalejandro.smkt.users.service.UserService;
//import com.netflix.discovery.DiscoveryClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	// @Autowired
	// private DiscoveryClient discoveryClient;

	@Value("${oauth.user}")
	private String appUser;

	@Value("${oauth.secret}")
	private String appSecret;

	@GetMapping("/all")
	public ResponseEntity<List<UserDTO>> getUsers(
			@RequestHeader(name = "Authorization", required = true) final String token) {

		log.info("Call users/all");

		List<UserDTO> users = userService.getUsers();

		return users.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(users, HttpStatus.OK);
	}

	@GetMapping("/search")
	public ResponseEntity<UserDTO> searchUser(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestParam(name = "username", required = false) final String username,
			@RequestParam(name = "email", required = false) final String email,
			@RequestParam(name = "id", required = false) final Long id) {

		Optional<UserDTO> user;

		if (username != null) {

			log.info("Call users/search?username={}", username);

			user = userService.getUsers().stream().filter(userItem -> userItem.getUsername().equals(username))
					.findFirst();

		} else if (email != null) {
			log.info("Call users/search?email={}", email);

			user = userService.getUsers().stream().filter(userItem -> userItem.getEmail().equals(email)).findFirst();

		} else if (id != null) {

			log.info("Call users/search?id={}", id);

			user = userService.getUsers().stream().filter(userItem -> userItem.getId().toString().equals(id.toString()))
					.findFirst();

		} else {

			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return user.isPresent() ? new ResponseEntity<>(user.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);

	}

	@PostMapping("/user")
	public ResponseEntity<UserDTO> create(@RequestHeader(name = "Authorization", required = true) final String token,
			final @RequestBody UserRegistrationDTO user) {

		log.info("Call users/create");

		log.debug("User -> {}", user.toString());

		final UserDTO newUser = userService.create(user);

		return newUser == null ? new ResponseEntity<>(HttpStatus.BAD_REQUEST)
				: new ResponseEntity<>(newUser, HttpStatus.CREATED);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<UserResponse> deleteUser(
			@RequestHeader(name = "Authorization", required = true) final String token, @PathVariable final Long id) {

		log.info("Call users/delete/{}", id);

		final UserResponse userResponse = userService.delete(id);

		return new ResponseEntity<>(userResponse, userResponse.getStatus());
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<UserResponse> putUserById(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestBody final UserUpdateRequest req, @PathVariable("id") final Long id) {

		log.info("Call users/id");

		final UserResponse userResponse = userService.updateUser(req, id);

		return userResponse.getUser() == null ? new ResponseEntity<>(userResponse.getStatus())
				: new ResponseEntity<>(userResponse, userResponse.getStatus());
	}

}
