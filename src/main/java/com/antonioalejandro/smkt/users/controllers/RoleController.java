package com.antonioalejandro.smkt.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.pojo.RoleDTO;
import com.antonioalejandro.smkt.users.service.IRoleService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private IRoleService roleService;
	
	
	@GetMapping("/all")
	public ResponseEntity<List<RoleDTO>> getRoles() {
		log.info("Call roles/all");
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
	}

	@GetMapping("/{id}")
	public ResponseEntity<RoleDTO> getRoleById(@PathVariable("id") final long id) {
		log.info("Call roles/{}", id);
		final RoleDTO role = roleService.getRoleById(id);
		return role == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(role, HttpStatus.OK);
	}
	
	
	
}
