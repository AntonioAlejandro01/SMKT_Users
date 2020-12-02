package com.antonioalejandro.smkt.users.controllers;

import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;
import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationDTO;
import com.antonioalejandro.smkt.users.pojo.UserResponse;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;

	private static final Logger log = Logger.getLogger(UserController.class.getName());

	@GetMapping("/all")
	public ResponseEntity<List<UserDTO>> getUsers() {
		log.log(Level.INFO, "Call users/all");
		return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
	}

	@GetMapping("/search/{username}")
	public ResponseEntity<UserDTO> searchUser(@PathVariable("username") final String username) {
		log.log(Level.INFO, "Call users/search/ {}", username);
		final Optional<UserDTO> user = userService.getUsers().stream()
				.filter(userItem -> userItem.getUsername().equals(username)).findFirst();
		return user.isPresent() ? new ResponseEntity<>(user.get(), HttpStatus.OK)
				: new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/byEmail")
	public ResponseEntity<List<UserDTO>> getUsersByEmail(@RequestBody final String email) {
		log.log(Level.INFO, "Call users/by");
		return new ResponseEntity<>(userService.getUsersByEmail(email), HttpStatus.OK);
	}

	@GetMapping("/byId/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final long id) {
		log.log(Level.INFO, "Call users/byId/{}", id);
		return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<UserDTO> create(final @RequestBody UserRegistrationDTO user) {
		log.log(Level.INFO, "Call users/create");
		final UserDTO newUser = userService.create(user);

		return new ResponseEntity<>(newUser, HttpStatus.OK);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") final Long id) {
		log.log(Level.INFO, "Call users/delete/{}", id);
		final UserResponse userResponse = userService.delete(id);
		return new ResponseEntity<>(userResponse.getMessage(), userResponse.getStatus());
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> putUserById(@RequestBody final UserRegistrationDTO userRegistrationDTO,
			@PathVariable("id") final Long id) {
		log.log(Level.INFO, "Call users/id");
		final UserResponse userResponse = userService.updateUser(userRegistrationDTO, id);
		return userResponse.getUser() == null ? new ResponseEntity<>(userResponse.getStatus())
				: new ResponseEntity<>(userResponse.getUser(), userResponse.getStatus());
	}

	@GetMapping("roles/all")
	public ResponseEntity<List<RoleDTO>> getRoles() {
		log.log(Level.INFO, "Call users/roles/all");
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
	}

	@GetMapping("roles/{id}")
	public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") final long id) {
		log.log(Level.INFO, "Call users/roles/{}", id);
		final RoleDTO role = roleService.getRoleById(id);
		return role == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(role, HttpStatus.OK);
	}

}
