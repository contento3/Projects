package com.contento3.web.helper;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

public class SpringContextHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private ApplicationContext context;

    public SpringContextHelper(UI application) {
//        ServletContext servletContext = ((WebApplicationContext) application.getSession().getSession().getServletContext();
    	ServletContext servletContext = VaadinServlet.getCurrent().getServletContext();
    	context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }

    public Object getBean(final String beanRef) {
        return context.getBean(beanRef);
    }
    
    public Object getBean(final String beanRef,Object parameter) {
        return context.getBean(beanRef,parameter);
    }    

}