package com.contento3.web;

import java.util.Collection;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.WebApplicationContext;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class OliveHome extends Application 
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public WebApplicationContext appContext;

	@Override
	public void init() {

		SpringContextHelper helper = new SpringContextHelper(this);
		Button logoutButton = new Button("logout");
		
		final OliveMainWindow main = new OliveMainWindow(helper,logoutButton);
		
		
		this.setMainWindow(main);
		
		//TODO put in a properties file
        setLogoutURL("/cms/j_spring_security_logout");

//		if (hasAnyRole(Roles.ROLE_ADMIN))
//		{
//			label = new Label("You have admin role.");
//		}
//		else
//		{
//			label = new Label("You have user role.");
//		}

		
		
		//main.addComponent(label);
		
		//main.addComponent(logout);
        
		logoutButton.addListener(new Button.ClickListener()
		{
            private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick(ClickEvent event)
			{
				getMainWindow().getApplication().close();
			}
		});
	}

	public boolean hasAnyRole(String... roles)
	{
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
		for (GrantedAuthority authority : authorities)
		{
			for (String role : roles)
			{
				if (role.equals(authority.getAuthority()))
				{
					return true;
				}
			}
		}
		return false;
	}

	public void setWebApplicationContext(WebApplicationContext appContext)
	{
		this.appContext = appContext;
	}

}