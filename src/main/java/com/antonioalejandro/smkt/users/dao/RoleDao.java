package com.antonioalejandro.smkt.users.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.antonioalejandro.smkt.users.entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

	public Role findByName(@Param("name") String name);
}
