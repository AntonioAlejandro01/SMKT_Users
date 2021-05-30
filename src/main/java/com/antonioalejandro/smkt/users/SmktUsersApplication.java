package com.antonioalejandro.smkt.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Smkt Users Application Class
 * 
 * @author AntopnioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
@SpringBootApplication
@EnableSwagger2
@EnableEurekaClient
public class SmktUsersApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmktUsersApplication.class, args);
	}

}
