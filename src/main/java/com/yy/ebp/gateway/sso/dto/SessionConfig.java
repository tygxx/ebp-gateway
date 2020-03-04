package com.yy.ebp.gateway.sso.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

@ConfigurationProperties(prefix="session")
public class SessionConfig {
	
	private List<String> except = new ArrayList<>();

	public List<String> getExcept() {
		return except;
	}
 
}
