/*
 * @Author AntonioAlejandro01
 * 
 * @link http://antonioalejandro.com
 * @link https://github.com/AntonioAlejandro01/SMKT_Users
 * 
 */
package com.antonioalejandro.smkt.users.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.pojo.response.RoleResponse;
import com.antonioalejandro.smkt.users.service.IRoleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

/**
 * The Class RoleController.
 */
@Api(value = "/roles", tags = { "Roles" })
@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {

	/** The role service. */
	@Autowired
	private IRoleService roleService;

	/**
	 * Gets the roles.
	 *
	 * @param token the token
	 * @return the roles
	 */
	@ApiOperation(value = "Get list of roles ", response = RoleResponse.class, tags = "Roles", responseContainer = "List")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Ok", response = RoleResponse.class, responseContainer = "List") })
	@GetMapping()
	public ResponseEntity<RoleResponse> getRoles(
			@RequestHeader(name = "Authorization", required = true) final String token) {
		log.info("Call getRoles");

		RoleResponse roleResponse = roleService.getRoles();
		
		if (roleResponse.getRoles().isEmpty()) {
			return new ResponseEntity<>(new RoleResponse(HttpStatus.NO_CONTENT,"No Content"),HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(roleResponse,HttpStatus.OK);
	}

	/**
	 * Gets the role by id.
	 *
	 * @param token the token
	 * @param name  the name
	 * @return the role by id
	 */
	@ApiOperation(value = "Get role by name ", response = RoleResponse.class, tags = "Roles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = RoleResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = RoleResponse.class),
			@ApiResponse(code = 404, message = "Not Found", response = RoleResponse.class) })
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "toke JWT", required = true, dataType = "string", paramType = "header", readOnly = true) })
	@GetMapping("/{name}")
	public ResponseEntity<RoleResponse> getRoleByName(
			@RequestHeader(name = "Authorization", required = true) final String token,
			@PathVariable(name = "name", required = true) final String name) {
		log.info("Call roles/{}", name);

		String ms = validateName(name);
		if (!ms.isEmpty()) {
			return new ResponseEntity<>(new RoleResponse(HttpStatus.BAD_REQUEST, ms), HttpStatus.BAD_REQUEST);
		}
		RoleResponse roleResponse = roleService.getRoleByName(name);
		return new ResponseEntity<>(roleResponse,
				roleResponse.haveData() ? HttpStatus.OK : roleResponse.getHttpStatus());
	}

	/**
	 * Validate name.
	 *
	 * @param name the name
	 * @return the string
	 */
	private String validateName(String name) {
		if (name == null || name.isBlank()) {
			return "Name is mandatory. ";
		}
		return "";
	}

}