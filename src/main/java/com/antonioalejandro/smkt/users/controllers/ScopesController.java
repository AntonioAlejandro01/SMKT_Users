package com.antonioalejandro.smkt.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.exceptions.BadRequestException;
import com.antonioalejandro.smkt.users.exceptions.NotFoundException;
import com.antonioalejandro.smkt.users.exceptions.RequestException;
import com.antonioalejandro.smkt.users.service.IScopesService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/scopes")
public class ScopesController {

	@Autowired
	private IScopesService scopeService;

	@ApiOperation(value = "Get list of scopes by the role id ", response = List.class, tags = "getScopesForRole")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = List.class),
			@ApiResponse(code = 204, message = "No Content", response = void.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Exception.class) })
	@GetMapping()
	public List<String> getScopesForRole(@RequestHeader(name = "Authorization", required = true) final String token,
			@RequestParam(name = "roleId", required = true) final Long roleId) throws RequestException {

		validateId(roleId);

		log.info("Call get scopes for role {}", roleId);

		return scopeService.getScopesForRole(roleId);
	}

	private void validateId(Long id) throws BadRequestException {
		String ms = "";
		if (id == null) {
			ms = "Role id is mandatory. ";
		} else {
			if (id < 1) {
				ms = "id can't be less or equal than zero. ";
			}
		}
		if (!ms.isEmpty()) {
			throw new BadRequestException(ms);
		}
	}
}
