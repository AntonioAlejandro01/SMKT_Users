package com.antonioalejandro.smkt.users.dao;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.antonioalejandro.smkt.users.entity.Role;

@RepositoryRestResource(path = "roles")
public interface RoleDao extends PagingAndSortingRepository<Role, Long> {

	public Role findByName(@Param("name") String name);
}
