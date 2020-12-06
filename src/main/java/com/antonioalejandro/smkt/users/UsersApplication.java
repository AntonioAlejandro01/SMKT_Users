/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class UsersApplication.
 */
@Slf4j
@SpringBootApplication
@EnableSwagger2
public class UsersApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String password = "password";
		String encodedPassword = passwordEncoder.encode(password);

		log.info("Password is         : {}", password);
		log.info("Encoded Password is         : {}", encodedPassword);

		SpringApplication.run(UsersApplication.class, args);
	}

}
