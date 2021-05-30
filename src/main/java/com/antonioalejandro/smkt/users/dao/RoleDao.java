package com.antonioalejandro.smkt.users.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.antonioalejandro.smkt.users.model.entity.Role;

/**
 * Role Dao Repository
 * 
 * @author AntonioAlejandro01 - www.antonioalejandro.com
 * @version 1.0.0
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
