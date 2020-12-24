/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.pojo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class TokenContent {

	@JsonProperty("user_name")
	private String username;
	
	private List<String> scope;
	
	private String name;
	
	private boolean active;
	
	private Long exp;
	
	private List<String> authorities;
	
	private String jti;
	
	private String email;
	
	@JsonProperty("client_id")
	private String clientId;
	
	private String lastname;
	
	@JsonProperty("username")
	private String usernameC;
	

}
