package com.antonioalejandro.haas.users.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import com.antonioalejandro.haas.users.entity.User;

@RepositoryRestResource(path = "users")
public interface UserDao extends PagingAndSortingRepository<User, Long> {

	public User findByUsernameAndEmail(String username, String email);

	@RestResource(path = "search-username")
	public User findByUsername(@Param("username") String username);

	@Query("select u from User u where u.username=?1 or u.email=?1")
	public User searchByUserNameOrEmail(String username);
}
