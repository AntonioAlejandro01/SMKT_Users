package com.antonioalejandro.haas.users.controllers;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.haas.users.entity.User;
import com.antonioalejandro.haas.users.service.UserService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserService userService;

	private static final Logger log = Logger.getLogger(UserController.class.getName());

	@GetMapping("/all")
	public ResponseEntity<List<User>> getUsers() {
		log.log(Level.INFO, "Call users/all");
		return new ResponseEntity<List<User>>(userService.getUsers(), HttpStatus.OK);
	}

	@GetMapping("/byEmail")
	public ResponseEntity<List<User>> getUsersByEmail(@RequestParam final String email) {
		return new ResponseEntity<List<User>>(userService.getUsersByEmail(email), HttpStatus.OK);
	}

	@GetMapping("/byId")
	public ResponseEntity<User> getUserById(@RequestParam final long id) {
		return new ResponseEntity<User>(userService.getUserById(id), HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<User> create(final @RequestBody User user) {
		return new ResponseEntity<User>(userService.create(user), HttpStatus.OK);
	}

	// DELETE
	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable("id") final Long id) {

		return userService.delete(id);
	}

	@PutMapping("/putUser")
	public String putUserById(@RequestBody final User user) {
		return userService.UpdateUser(user);
	}

}
