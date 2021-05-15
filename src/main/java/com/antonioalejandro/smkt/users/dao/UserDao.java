/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.antonioalejandro.smkt.users.model.entity.User;

/**
 * The Interface UserDao.
 */
public interface UserDao extends PagingAndSortingRepository<User, Long> {

	/**
	 * Find by username and email.
	 *
	 * @param username the username
	 * @param email    the email
	 * @return the user
	 */
	public User findByUsernameAndEmail(String username, String email);

	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User findByUsername(@Param("username") String username);

	/**
	 * Find by email.
	 *
	 * @param username the username
	 * @return the user
	 */
	public User findByEmail(@Param("email") String username);

	/**
	 * Search by user name or email.
	 *
	 * @param username the username
	 * @return the user
	 */
	@Query("select u from User u where u.username=?1 or u.email=?1")
	public User searchByUserNameOrEmail(String username);

	/**
	 * Gets the users same username.
	 *
	 * @param username the username
	 * @return the users same username
	 */
	@Query(nativeQuery = true, value = "select count(id) from users where username = :username")
	public Long getUsersSameUsername(@Param(value = "username") String username);

	/**
	 * Gets the users same email.
	 *
	 * @param email the email
	 * @return the users same email
	 */
	@Query(nativeQuery = true, value = "select count(id) from users where email = :email")
	public Long getUsersSameEmail(@Param(value = "email") String email);

	/**
	 * Gets the id role by user id.
	 *
	 * @param id the id
	 * @return the id role by user id
	 */
	@Query(nativeQuery = true, value = "select role_id from users where id= :id")
	public Long getIdRoleByUserId(@Param("id") Long id);
}
