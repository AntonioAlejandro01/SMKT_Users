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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.antonioalejandro.smkt.users.pojo.TokenData;

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

	@Value("${oauth.id}")
	private String oauthId;

	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * Gets the data token.
	 *
	 * @param token the token
	 * @return the data token
	 */
	public TokenData getDataToken(String token) {
		WebClient client = WebClientFactory
				.getWebClient(WebClientFactory.getURLInstanceService(oauthId, discoveryClient));

		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("token", token.split(" ")[1]);

		TokenData data;
		try {
			data = client.post().uri("/oauth/check_token").contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.body(BodyInserters.fromFormData(body)).retrieve().bodyToMono(TokenData.class).block();
		} catch (WebClientResponseException e) {
			throw new WebClientResponseException(401, HttpStatus.UNAUTHORIZED.toString(), HttpHeaders.EMPTY, null,
					null);
		}

		return data;

	}

	/**
	 * Checks if is authorized.
	 *
	 * @param token           the token
	 * @param scopesPermitted the scopes permitted
	 * @return true, if is authorized
	 */
	public boolean isAuthorized(List<String> scopesPermitted, TokenData tokenData) {
		if (scopesPermitted == null || scopesPermitted.isEmpty()) {
			return false;
		}
		for (String scope : scopesPermitted) {
			if (tokenData.getScope().contains(scope)) {
				return true;
			}
		}
		return false;

	}
}
