/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.antonioalejandro.smkt.users.entity.Role;

/**
 * The Interface RoleDao.
 */
public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

	/**
	 * Find by name.
	 *
	 * @param name the name
	 * @return the role
	 */
	public Role findByName(@Param("name") String name);
}
