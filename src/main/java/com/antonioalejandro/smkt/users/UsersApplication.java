package com.antonioalejandro.smkt.users;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class UsersApplication {

	private static final Logger log = Logger.getLogger(UsersApplication.class.getName());

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = "password";
		String encodedPassword = passwordEncoder.encode(password);
		log.log(Level.INFO, "Password is         : {}", password);
		log.log(Level.INFO, "Encoded Password is         : {}", encodedPassword);

		SpringApplication.run(UsersApplication.class, args);
	}

}
