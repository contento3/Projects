package com.contento3.web.common.helper;

import javax.servlet.http.HttpSession;

import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Window;

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
	public static Object loadAttribute(final Window window,final String parameter){
		return loadHttpSession(window).getAttribute(parameter);
	}
	
	/**
	 * Loads the HttpSession.
	 * @param window ParentWindow
	 * @return
	 */
	public static HttpSession loadHttpSession(final Window window){
        final WebApplicationContext ctx = ((WebApplicationContext) window.getApplication().getContext());
        final HttpSession session = ctx.getHttpSession();
        return session;
	} 
}
