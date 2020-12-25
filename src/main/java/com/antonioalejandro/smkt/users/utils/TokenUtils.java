/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.web.reactive.function.client.WebClient;

import com.antonioalejandro.smkt.users.pojo.TokenContent;

import lombok.Getter;

/**
 * The Class TokenUtils.
 */
public class TokenUtils {

	/** The Constant TOKEN_KEY. */
	public static final String TOKEN_KEY = "Bearer ";

	/** The app user. */
	@Value("${oauth.user}")
	private String appUser;

	/** The app secret. */
	@Value("${oauth.secret}")
	private String appSecret;

	@Value("$oauth.id")
	private String oauthId;

	@Getter
	private String token;

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * Gets the data token.
	 *
	 * @param token the token
	 * @return the data token
	 */
	public TokenContent getDataToken(String token) {
		WebClient client = WebClientFactory
				.getWebClient(WebClientFactory.getURLInstanceService(oauthId, discoveryClient), appUser, appSecret);
		HttpEntity<TokenContent> entity = client.post().uri("/oauth/check_token")
				.body(String.format("token=%s", token), String.class).retrieve().toEntity(TokenContent.class).block();
		return entity.getBody();

	}

	/**
	 * Checks if is authorized.
	 *
	 * @param token           the token
	 * @param scopesPermitted the scopes permitted
	 * @return true, if is authorized
	 */
	public boolean isAuthorized(List<String> scopesPermitted, TokenContent tokenContent) {
		if (scopesPermitted== null || scopesPermitted.isEmpty()) {
			return false;
		}
		for (String scope : scopesPermitted) {
			if (tokenContent.getScope().contains(scope)) {
				return true;
			}
		}
		return false;
		
		

	}
}
