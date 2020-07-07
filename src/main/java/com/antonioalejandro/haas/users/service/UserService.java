package com.antonioalejandro.haas.users.service;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.antonioalejandro.haas.users.dao.UserDao;
import com.antonioalejandro.haas.users.entity.User;

public class UserService implements IUserService {
	@Autowired
	UserDao repository;

	private static final Logger log = Logger.getLogger(UserService.class.getName());

	@Override
	public List<User> getUsers() {
		log.log(Level.INFO, "Call to getUsers");
		return StreamSupport.stream(repository.findAll().spliterator(), false).collect(Collectors.toList());
	}

	@Override
	public List<User> getUsersByEmail(final String email) {
		log.log(Level.INFO, "Call to getUsersByEmail");
		final String searchValue = email.toLowerCase();
		final List<User> list = StreamSupport.stream(repository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		list.removeIf(x -> !x.getEmail().contains(searchValue)
				&& !(x.getName().toLowerCase() + " " + x.getLastname().toLowerCase()).contains(searchValue));
		return list;
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserById(final long id) {
		log.log(Level.INFO, "Call to getUserById");
		return repository.findById(id).get();
	}

	@Override
	@Transactional
	public String UpdateUser(final User user) {
		log.log(Level.INFO, "Call to UpdateUser");
		if (repository.findById(user.getId()).isPresent()) {
			user.setPassword(user.getPassword());
			user.setEmail(user.getEmail());
			user.setName(user.getName());
			user.setLastname(user.getLastname());
			user.setPhoto(user.getPhoto());
			repository.save(user);
			return "Usuario actualizado";
		} else {
			return "No existe el usuario";
		}
	}

	@Override
	@Transactional
	public String delete(final Long id) {
		if (repository.findById(id).isPresent()) {
			repository.deleteById(id);
			return "Usuario borrado";
		} else {
			return "No existe el usuario";
		}
	}

	@Override
	@Transactional
	public User create(final User user) {
		log.log(Level.INFO, "Call to Save");
		user.setPassword(getSHA256(user.getPassword()));
		if (user.getPassword() == null) {
			return null;
		}
		return repository.save(user);
	}

	@Override
	public boolean verifyUser(final String usernameOrEmail, final String password) {
		final User user = repository.searchByUserNameOrEmail(usernameOrEmail);
		final String passwordToCompare = getSHA256(password);
		return passwordToCompare != null && passwordToCompare.equals(user.getPassword());
	}

	private String getSHA256(final String input) {

		String toReturn = null;
		try {
			final MessageDigest digest = MessageDigest.getInstance("SHA-256");
			digest.reset();
			digest.update(input.getBytes("utf8"));
			toReturn = String.format("%064x", new BigInteger(1, digest.digest()));
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return toReturn;
	}

}
