/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.model.response.ScopeResponse;
import com.antonioalejandro.smkt.users.service.ScopesService;
import com.antonioalejandro.smkt.users.utils.Validations;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class ScopesController.
 */
@RestController
@Slf4j
@RequestMapping("/scopes")
@Api(value = "/scopes", tags = { "Scopes" }, produces = "application/json")
public class ScopesController {

	/** The scope service. */
	@Autowired
	private ScopesService scopeService;

	/**
	 * Gets the scopes for role.
	 *
	 * @param roleId the role id
	 * @return the scopes for role
	 */
	@ApiOperation(value = "Get list of scopes by the role id ", response = ScopeResponse.class, tags = "Scopes")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = ScopeResponse.class),
			@ApiResponse(code = 204, message = "No Content", response = void.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ScopeResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = ScopeResponse.class) })
	@GetMapping()
	public ResponseEntity<ScopeResponse> getScopesForRole(
			@RequestParam(name = "roleId", required = true) final Long roleId) {

		String ms = Validations.validateId(roleId, true);

		if (!ms.isEmpty()) {
			return new ResponseEntity<>(new ScopeResponse(HttpStatus.BAD_REQUEST, ms), HttpStatus.BAD_REQUEST);
		}

		log.info("Call get scopes for role {}", roleId);

		ScopeResponse response = scopeService.getScopesForRole(roleId);

		if (!response.haveData()) {
			return new ResponseEntity<>(response, response.getHttpStatus());
		}

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
