package com.olive.web.security;

import org.springframework.stereotype.Component;

@Component("securedService")
public class SecuredServiceImpl implements SecuredService
{
	@Override
	public void doSomething()
	{
		System.out.println("You are executing secured method1");
	}

	@Override
    public void adminSomething()
    {
	    // TODO Auto-generated method stub

    }

}
