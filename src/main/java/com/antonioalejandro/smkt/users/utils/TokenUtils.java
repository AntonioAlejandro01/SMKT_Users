package com.antonioalejandro.smkt.users.utils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;

import com.antonioalejandro.smkt.users.pojo.TokenContent;

public class TokenUtils {

	private final static String TOKENKEY = "Bearer ";

	private TokenUtils() {
	}

	/**
	 * 
	 * @param token
	 * @return TokenContent
	 */
	public TokenContent getDataToken(String token, String appUser, String appSecret, String address, String port) {
		if (token.contains(TOKENKEY)) {
			token = token.replace(TOKENKEY, "");
		}

		String basicAuthHeader = "basic " + Base64Utils.encodeToString((appUser + ":" + appSecret).getBytes());

		WebClient webClient = WebClient.builder().baseUrl("http://" + address + ":" + port)
				.defaultHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader).build();

		return webClient.post().uri(URI.create("/oauth/check_token")).bodyValue("token=" + token)
				.acceptCharset(StandardCharsets.UTF_8).retrieve().bodyToMono(TokenContent.class).block();

	}
}
