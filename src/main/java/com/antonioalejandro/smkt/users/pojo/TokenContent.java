/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.pojo;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class TokenContent {

	/** The id. */
	private Long id;

	/** The token. */
	@NonNull
	private String token;
	
	public TokenContent() {
		id = 2L;
		token = "";
	}

}
