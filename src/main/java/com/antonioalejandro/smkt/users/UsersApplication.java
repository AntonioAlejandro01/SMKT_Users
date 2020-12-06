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

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The Class UsersApplication.
 */
@SpringBootApplication
@EnableSwagger2
public class UsersApplication {

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(UsersApplication.class, args);
	}

}
