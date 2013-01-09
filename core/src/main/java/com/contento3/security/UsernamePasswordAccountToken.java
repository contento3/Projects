package com.contento3.security;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UsernamePasswordAccountToken extends UsernamePasswordToken {

	private static final long serialVersionUID = 1L;
	private Integer accountId;

	public UsernamePasswordAccountToken(final String username,final String password,final Integer accountId){
		super(username,password);
		this.accountId = accountId;
	}
		
	public void setAccountId(final Integer accountId) {
		this.accountId = accountId;
	}

	public Integer getAccountId() {
		return accountId;
	}
	
	
}
