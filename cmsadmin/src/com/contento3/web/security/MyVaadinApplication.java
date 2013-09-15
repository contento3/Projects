package com.contento3.web.security;

import org.springframework.web.context.WebApplicationContext;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

public class MyVaadinApplication extends UI
{
	private static final long serialVersionUID = 1L;

	//@Autowired
	//private SecuredService securedService;

	public WebApplicationContext appContext;

	@Override
	protected void init(VaadinRequest request) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void init()
//	{
//		Window window;
//		Label label;
//		Button logout;
//
//		window = new Window("My Vaadin Application");
//
////		setMainWindow(window);
////		setLogoutURL("/vaadin-spring-jsp/j_spring_security_logout");
//
//		if (hasAnyRole(Roles.ROLE_ADMIN))
//		{
//			label = new Label("You have admin role.");
//		}
//		else
//		{
//			label = new Label("You have user role.");
//		}
//
//		logout = new Button("logout");
//		logout.addListener(new Button.ClickListener()
//		{
//            private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick(ClickEvent event)
//			{
//				getMainWindow().getApplication().close();
//			}
//		});
//
////		window.addComponent(label);
////		window.addComponent(logout);
//	}


//	public boolean hasAnyRole(String... roles)
//	{
//		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//		Collection<GrantedAuthority> authorities = (Collection<GrantedAuthority>) authentication.getAuthorities();
//		for (GrantedAuthority authority : authorities)
//		{
//			for (String role : roles)
//			{
//				if (role.equals(authority.getAuthority()))
//				{
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//

//	public void setWebApplicationContext(WebApplicationContext appContext)
//	{
//		this.appContext = appContext;
//	}
//

//	@Override
//	protected void init(VaadinRequest request) {
//		// TODO Auto-generated method stub
//		
//	}

}