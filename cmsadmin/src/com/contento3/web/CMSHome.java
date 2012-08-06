package com.contento3.web;

import org.springframework.web.context.WebApplicationContext;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.Application;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

/**
 * This is the initiating class for cms ui
 * @author HAMMAD
 *
 */
public class CMSHome extends Application 
{

	private static final long serialVersionUID = 1L;
	
	/**
	 * Webapplication context
	 */
	public WebApplicationContext appContext;

	@Override
	public void init() {
		
		//Sets the theme for the application.
		setTheme("contento3");
		
		final SpringContextHelper helper = new SpringContextHelper(this);
		final Button logoutButton = new Button("Log Out");
		logoutButton.addStyleName("link");
		
		//TODO put in a properties file
        setLogoutURL("/cms/j_spring_security_logout");
        		
		final CMSMainWindow main = new CMSMainWindow(helper,logoutButton);
		this.setMainWindow(main);

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

	public void setWebApplicationContext(WebApplicationContext appContext)
	{
		this.appContext = appContext;
	}

}