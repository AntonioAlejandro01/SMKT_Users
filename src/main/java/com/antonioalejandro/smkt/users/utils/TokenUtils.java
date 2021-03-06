package com.antonioalejandro.smkt.users.utils;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import com.antonioalejandro.smkt.users.config.AppEnviroment;
import com.antonioalejandro.smkt.users.model.TokenData;

/**
 * Token Utils Class
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
 */
public class TokenUtils {

	/** The env. */
	@Autowired
	private AppEnviroment env;

	/** The discovery client. */
	@Autowired
	private DiscoveryClient discoveryClient;

	/**
	 * Gets the data token.
	 *
	 * @param token the token
	 * @return the data token
	 */
	public TokenData getDataToken(String token) {
		var client = WebClientFactory
				.getWebClient(WebClientFactory.getURLInstanceService(env.getOauthId(), discoveryClient));

		LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add(Constants.TOKEN_FIELD_NAME, token.split(" ")[1]);

		TokenData data;
		try {
			data = client.post().uri(env.getOauthPath()).contentType(MediaType.APPLICATION_FORM_URLENCODED)
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
	 * @param scopesPermitted the scopes permitted
	 * @param tokenData       the token data
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
