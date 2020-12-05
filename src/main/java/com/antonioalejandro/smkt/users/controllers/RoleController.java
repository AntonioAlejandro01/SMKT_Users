package com.antonioalejandro.smkt.users.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.antonioalejandro.smkt.users.exceptions.BadRequestException;
import com.antonioalejandro.smkt.users.exceptions.NotFoundException;
import com.antonioalejandro.smkt.users.exceptions.RequestException;
import com.antonioalejandro.smkt.users.pojo.RoleDTO;
import com.antonioalejandro.smkt.users.service.IRoleService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/roles")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	@ApiOperation(value = "Get list of roles ", response = List.class, tags = "getRoles")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = List.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Exception.class) })
	@GetMapping()
	public List<RoleDTO> getRoles(@RequestHeader(name = "Authorization", required = true) final String token) {
		log.info("Call getRoles");

		return roleService.getRoles();
	}

	@ApiOperation(value = "Get role by name ", response = RoleDTO.class, tags = "getRoleById")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Ok", response = RoleDTO.class),
			@ApiResponse(code = 400, message = "Bad Request", response = BadRequestException.class),
			@ApiResponse(code = 404, message = "Not Found", response = NotFoundException.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = Exception.class) })
	@GetMapping("/{name}")
	public RoleDTO getRoleById(@RequestHeader(name = "Authorization", required = true) final String token,
			@PathVariable("name") final String name) throws RequestException {
		log.info("Call roles/{}", name);

		String ms = validateName(name);
		if (!ms.isEmpty()) {
			throw new BadRequestException(ms);
		}

		return roleService.getRoleByName(name);
	}

	private String validateName(String name) {
		if (name.isBlank()) {
			return "Name is mandatory. ";
		}
		return "";
	}

}
