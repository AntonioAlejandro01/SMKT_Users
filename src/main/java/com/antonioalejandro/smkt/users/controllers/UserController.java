package com.antonioalejandro.smkt.users.controllers;

import java.util.List;
import java.util.Optional;
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
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.entity.User;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;
import com.antonioalejandro.smkt.users.pojo.UserDTO;
import com.antonioalejandro.smkt.users.pojo.UserRegistrationDTO;
import com.antonioalejandro.smkt.users.pojo.UserResponse;
import com.antonioalejandro.smkt.users.service.RoleService;
import com.antonioalejandro.smkt.users.service.UserService;

@CrossOrigin(origins = "*")
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
		return new ResponseEntity<List<UserDTO>>(userService.getUsers(), HttpStatus.OK);
	}
	@GetMapping("/search/{username}")
	public ResponseEntity<UserDTO> searchUser(@PathVariable("username") final String username){
		log.log(Level.INFO, "Call users/search/" + username);
		final Optional<UserDTO> user = userService.getUsers().stream().filter(userItem -> {
			return userItem.getUsername().equals(username);
		}).findFirst();
		return user.isPresent() ? new ResponseEntity<UserDTO>(user.get(),HttpStatus.OK): new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@GetMapping("/byEmail")
	public ResponseEntity<List<UserDTO>> getUsersByEmail(@RequestBody final String email) {
		log.log(Level.INFO, "Call users/by");
		return new ResponseEntity<List<UserDTO>>(userService.getUsersByEmail(email), HttpStatus.OK);
	}

	@GetMapping("/byId/{id}")
	public ResponseEntity<UserDTO> getUserById(@PathVariable("id") final long id) {
		log.log(Level.INFO, "Call users/byId/" + id);
		return new ResponseEntity<UserDTO>(userService.getUserById(id), HttpStatus.OK);
	}

	@PostMapping("/user")
	public ResponseEntity<UserDTO> create(final @RequestBody UserRegistrationDTO user) {
		log.log(Level.INFO, "Call users/create");
		final UserDTO newUser = userService.create(user);
		
		return new ResponseEntity<UserDTO>(newUser,HttpStatus.OK);
	}

	@DeleteMapping("/user/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") final Long id) {
		log.log(Level.INFO, "Call users/delete/" + id);
		final UserResponse userResponse = userService.delete(id);
		return new ResponseEntity<String>(userResponse.getMessage(),userResponse.getStatus());
	}

	@PutMapping("/user/{id}")
	public ResponseEntity<User> putUserById(@RequestBody final UserRegistrationDTO userRegistrationDTO, @PathVariable("id") final Long id) {
		log.log(Level.INFO, "Call users/id");
		final UserResponse userResponse = userService.UpdateUser(userRegistrationDTO, id);
		return userResponse.getUser() == null ? new ResponseEntity<User>(userResponse.getStatus()): new ResponseEntity<User>(userResponse.getUser(), userResponse.getStatus());
	}
	
	@GetMapping("roles/all")
	public ResponseEntity<List<RoleDTO>> getRoles(){
		log.log(Level.INFO, "Call users/roles/all");
		return new ResponseEntity<List<RoleDTO>>(roleService.getRoles(),HttpStatus.OK);
	}
	
	@GetMapping("roles/{id}")
	public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id")final long id){
		log.log(Level.INFO, "Call users/roles/"+id);
		final RoleDTO role = roleService.getRoleById(id);
		return role == null ? new ResponseEntity<RoleDTO>(HttpStatus.NO_CONTENT):new ResponseEntity<RoleDTO>(role, HttpStatus.OK);
	}

}
