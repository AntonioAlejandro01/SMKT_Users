package com.antonioalejandro.smkt.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.service.IRoleService;
import com.antonioalejandro.smkt.users.service.IScopesService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/scopes")
public class ScopesController {
	
	@Autowired
	private IScopesService scopeService;
	
	@Autowired
	private IRoleService roleService;

	@GetMapping()
	public ResponseEntity<List<String>> getScopesForRole(
			@RequestParam(name = "roleId", required = true) final Long roleId) {

		log.info("Call get scopes for role {}", roleId);
		
		if (roleService.getRoleById(roleId) == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		List<String> scopes = scopeService.getScopesForRole(roleId);

		return scopes.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT): new ResponseEntity<List<String>>(scopes, HttpStatus.OK);
	}
}
