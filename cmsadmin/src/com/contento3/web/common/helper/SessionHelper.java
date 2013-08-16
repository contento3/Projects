package com.contento3.web.common.helper;

import com.vaadin.server.VaadinSession;

/**
 * Helper class to load and set parameters from session.
 * @author HAMMAD
 *
 */
public class SessionHelper {

	/**
	 * Loads parameter from the Session. 
	 * It requires the parentwindow as an argument to 
	 * get the session and the name of the parameter
	 */
	public static Object loadAttribute(final String parameter){
		return VaadinSession.getCurrent().getSession().getAttribute(parameter);
	}
	
	/**
	 * Loads the HttpSession.
	 * @param window ParentWindow
	 * @return
	 */
//	public static HttpSession loadHttpSession(final Window window){
//    	ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
//        final WebApplicationContext ctx = ((WebApplicationContext) window.getApplication().getContext());
//        return VaadinSession.getCurrent().getSession().getAttribute(name);
//	} 
}
