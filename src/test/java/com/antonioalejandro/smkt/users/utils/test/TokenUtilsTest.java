package com.antonioalejandro.smkt.users.utils.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.pojo.TokenContent;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

class TokenUtilsTest {

	private final static String TOKEN = "asd";
	private final static Long DATAOK = 2L;

	@Test
	void test() throws Exception {
		TokenContent x = TokenUtils.getDataToken(TOKEN);
		assertEquals(DATAOK,x.getId());
		assertEquals(TokenUtils.TOKEN_KEY + TOKEN,x.getToken());
		
		assertEquals(true, TokenUtils.isAuthorized(TOKEN, Arrays.asList("")));
	}
}
