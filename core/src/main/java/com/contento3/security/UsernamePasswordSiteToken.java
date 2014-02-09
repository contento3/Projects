package com.contento3.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordSiteToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	private Integer siteId;

	public UsernamePasswordSiteToken(final String username,final String password,final Integer siteId){
		super(username,password);
		this.siteId = siteId;
	}
		
	public void setAccountId(final Integer siteId) {
		this.siteId = siteId;
	}

	public Integer getSiteId() {
		return siteId;
	}
	
	
}
