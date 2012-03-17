package com.contento3.web.security;

import org.springframework.stereotype.Component;

@Component("securedService")
public class SecuredServiceImpl implements SecuredService
{
	@Override
	public void doSomething()
	{
	}

	@Override
    public void adminSomething()
    {
	    // TODO Auto-generated method stub

    }

}
