package com.antonioalejandro.smkt.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.pojo.BadRequestResponse;
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
	public ResponseEntity<List<RoleDTO>> getRoles(
			@RequestHeader(name = "Authorization", required = true) final String token) {
		log.info("Call roles/all");
		return new ResponseEntity<>(roleService.getRoles(), HttpStatus.OK);
	}

	@GetMapping("/{name}")
	public ResponseEntity<?> getRoleById(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@PathVariable("name") final String name) {
		log.info("Call roles/{}", name);
		
		String ms = validateName(name);
		if (!ms.isEmpty()) {
			return new ResponseEntity<>(new BadRequestResponse(ms, HttpStatus.BAD_REQUEST.toString()),
					HttpStatus.BAD_REQUEST);
		}
		
		final RoleDTO role = roleService.getRoleByName(name);
		return role == null ? new ResponseEntity<>(HttpStatus.NO_CONTENT) : new ResponseEntity<>(role, HttpStatus.OK);
	}
	
	private String validateName(String name) {
		if (name.isBlank()) {
			return "Name is mandatory. ";
		}
		return "";
	}

}
