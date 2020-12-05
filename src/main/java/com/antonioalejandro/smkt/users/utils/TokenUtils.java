package com.antonioalejandro.smkt.users.utils;

import java.net.URI;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.util.Base64Utils;
import org.springframework.web.reactive.function.client.WebClient;

import com.antonioalejandro.smkt.users.pojo.TokenContent;

public class TokenUtils {

	private static final String TOKEN_KEY = "Bearer ";

	@Value("${oauth.user}")
	private static String appUser;

	@Value("${oauth.secret}")
	private static String appSecret;
	
	private TokenUtils() {
	}

	/**
	 * 
	 * @param token
	 * @return TokenContent
	 */
	public static TokenContent getDataToken(String token) {
		if (token.contains(TOKEN_KEY)) {
			token = token.replace(TOKEN_KEY, "");
		}

		String basicAuthHeader = "basic " + Base64Utils.encodeToString((appUser + ":" + appSecret).getBytes());

		//WebClient webClient = WebClient.builder().baseUrl("http://" + "" + ":" + 3333)
			//	.defaultHeader(HttpHeaders.AUTHORIZATION, basicAuthHeader).build();

		//return webClient.post().uri(URI.create("/oauth/check_token")).bodyValue("token=" + token)
			//	.acceptCharset(StandardCharsets.UTF_8).retrieve().bodyToMono(TokenContent.class).block();

		return new TokenContent();
	}
	/**
	 * 
	 * @param token
	 * @param scope
	 * @return boolean
	 */
	public static boolean isAuthorized(String token, String scope) {
		return true;
	}
}
