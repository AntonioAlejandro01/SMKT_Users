package com.antonioalejandro.smkt.users.service;

import java.util.List;

public interface IScopesService {
	
	public List<String> getScopesForRole(Long roleId);
}
