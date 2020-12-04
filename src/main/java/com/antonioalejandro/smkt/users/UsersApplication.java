package com.antonioalejandro.smkt.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class UsersApplication {

	public static void main(String[] args) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		
		String password = "password";
		String encodedPassword = passwordEncoder.encode(password);
		
		log.info("Password is         : {}", password);
		log.info("Encoded Password is         : {}", encodedPassword);

		SpringApplication.run(UsersApplication.class, args);
	}

}
