package com.antonioalejandro.smkt.users.utils.test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.antonioalejandro.smkt.users.pojo.TokenContent;
import com.antonioalejandro.smkt.users.utils.TokenUtils;

class TokenUtilsTest {

	private final static String TOKEN = "asd";

	@Test
	void test() throws Exception {
		TokenUtils utils = new TokenUtils(TOKEN);
		TokenContent x = utils.getDataToken(TOKEN);
		assertThat(x).isInstanceOf(TokenContent.class);
		assertEquals(TokenUtils.TOKEN_KEY + TOKEN, x.getToken());

		assertEquals(true, utils.isAuthorized(Arrays.asList("")));
		assertEquals(false, utils.isAuthorized(null));
		assertEquals(false, utils.isAuthorized(null));
	}
}
