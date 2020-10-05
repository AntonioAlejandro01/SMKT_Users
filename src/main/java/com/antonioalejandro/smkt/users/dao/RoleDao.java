package com.antonioalejandro.smkt.users.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.antonioalejandro.smkt.users.entity.Role;

public interface RoleDao extends PagingAndSortingRepository<Role, Long> {
	
}
