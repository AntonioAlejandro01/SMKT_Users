package com.antonioalejandro.smkt.users.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.antonioalejandro.smkt.users.entity.User;

public interface UserDao extends PagingAndSortingRepository<User, Long> {

	public User findByUsernameAndEmail(String username, String email);

	public User findByUsername(@Param("username") String username);

	public User findByEmail(@Param("email") String username);

	@Query("select u from User u where u.username=?1 or u.email=?1")
	public User searchByUserNameOrEmail(String username);

	@Query(nativeQuery = true, value = "select count(id) from users where username = :username")
	public Long getUsersSameUsername(@Param(value = "username") String username);

	@Query(nativeQuery = true, value = "select count(id) from users where email = :email")
	public Long getUsersSameEmail(@Param(value = "email") String email);

	@Query(nativeQuery = true, value = "select role_id from users where id= :id")
	public Long getIdRoleByUserId(@Param("id") Long id);
}
