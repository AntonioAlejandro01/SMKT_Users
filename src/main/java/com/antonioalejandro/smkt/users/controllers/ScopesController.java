package com.antonioalejandro.smkt.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.pojo.BadRequestResponse;
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
	public ResponseEntity<?> getScopesForRole(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestParam(name = "roleId", required = true) final Long roleId) {

		String ms = validateId(roleId);
		if (!ms.isEmpty()) {
			return new ResponseEntity<>(new BadRequestResponse(ms, HttpStatus.BAD_REQUEST.toString()),
					HttpStatus.BAD_REQUEST);
		}

		log.info("Call get scopes for role {}", roleId);

		if (roleService.getRoleById(roleId) == null) {
			return new ResponseEntity<>(
					new BadRequestResponse("Role id doesn't exists. ", HttpStatus.BAD_REQUEST.toString()),
					HttpStatus.BAD_REQUEST);
		}

		List<String> scopes = scopeService.getScopesForRole(roleId);

		return scopes.isEmpty() ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
				: new ResponseEntity<>(scopes, HttpStatus.OK);
	}

	private String validateId(Long id) {
		if (id == null) {
			return "Role id is mandatory. ";
		} else {
			if (id < 1) {
				return "id can't be less or equal than zero. ";
			}
		}
		return "";
	}
}
