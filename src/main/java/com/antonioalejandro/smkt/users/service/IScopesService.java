package com.antonioalejandro.smkt.users.service;

import java.util.List;

import com.antonioalejandro.smkt.users.exceptions.RequestException;

public interface IScopesService {
	
	public List<String> getScopesForRole(Long roleId) throws RequestException;
}
