package com.contento3.web;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.WebApplicationContext;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.Application;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.LoginForm;
import com.vaadin.ui.LoginForm.LoginEvent;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * This is the initiating class for cms ui
 * @author HAMMAD
 *
 */
public class CMSHome extends Application 
{
	
	private static final Logger LOGGER = Logger.getLogger(CMSHome.class);


	private static final long serialVersionUID = 1L;
	
	/**
	 * Webapplication context
	 */
	public WebApplicationContext appContext;

	@Override
	public void init() {
		final SpringContextHelper helper = new SpringContextHelper(this);
		final Button logoutButton = new Button("Log Out");
		
		//Sets the theme for the application.
		setTheme("contento3");

        final CMSMainWindow main = new CMSMainWindow(helper);
        this.setMainWindow(main);
	}

	public void setWebApplicationContext(WebApplicationContext appContext)
	{
		this.appContext = appContext;
	}

}