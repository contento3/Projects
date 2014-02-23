package com.contento3.web;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;

import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * This is the initiating class for cms ui
 * @author HAMMAD
 *
 */
@Theme("contento3")
public class CMSHome extends UI 
{
	
	private static final Logger LOGGER = Logger.getLogger(CMSHome.class);


	private static final long serialVersionUID = 1L;
	
	/**
	 * Webapplication context
	 */
	public WebApplicationContext appContext;

//	@Override
//	public void init() {
//		final SpringContextHelper helper = new SpringContextHelper(this);
//		final Button logoutButton = new Button("Log Out");
//		
//		//Sets the theme for the application.
//		setTheme("contento3");
//
//        final CMSMainWindow main = new CMSMainWindow(helper);
//        this.setMainWindow(main);
//	}

	public void setWebApplicationContext(WebApplicationContext appContext)
	{
		this.appContext = appContext;
	}

	@Override
	protected void init(VaadinRequest request) {
		final SpringContextHelper helper = new SpringContextHelper(this);
		
		Boolean isDemo = false;
		final String dmParam = request.getParameter("dm");
		if (null!=dmParam && dmParam.equalsIgnoreCase("yes")){
			isDemo = true;
		}
			
        final CMSMainWindow main = new CMSMainWindow(helper,isDemo);
        final VerticalLayout view = new VerticalLayout();
        view.addComponent(main);
        view.setComponentAlignment(main, Alignment.BOTTOM_CENTER);
        setContent(view); 
	}

}