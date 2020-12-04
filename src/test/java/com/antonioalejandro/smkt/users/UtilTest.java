package com.antonioalejandro.smkt.users;

import java.util.HashSet;

import com.antonioalejandro.smkt.users.entity.Role;
import com.antonioalejandro.smkt.users.entity.Scope;
import com.antonioalejandro.smkt.users.entity.User;

public class UtilTest {

	public static final String DATAOK = "ok";
	public static final String DATAKO = "ko";

	public static final Long LONGOK = 1L;

	public static final Long LONGKO = 0L;

	public static Role getRole() {
		Role role = new Role();

		role.setId(UtilTest.LONGOK);
		role.setName(UtilTest.DATAOK);

		HashSet<Scope> scopes = new HashSet<>();

		scopes.add(getScope());

		role.setScopes(scopes);

		return role;
	}

	public static Scope getScope() {
		Scope scope = new Scope();

		scope.setId(UtilTest.LONGOK);
		scope.setName(UtilTest.DATAOK);

		return scope;
	}

	public static User getUser() {

		User user = new User();
		setDataUser(user, true);
		return user;
	}

	public static void modifyUser(User user) {
		setDataUser(user, false);
	}

	private static void setDataUser(User user, boolean isOk) {
		String data = isOk ? DATAOK : DATAKO;

		user.setEmail(data);
		user.setLastname(data);
		user.setName(data);
		user.setUsername(data);
		user.setPassword(data);
		user.setId(isOk ? LONGOK : LONGKO);
		user.setRole(isOk ? getRole() : new Role(LONGKO, DATAKO, null));

	}

}
