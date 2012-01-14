package com.contento3.web.helper;

import java.io.Serializable;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.vaadin.Application;
import com.vaadin.terminal.gwt.server.WebApplicationContext;

public class SpringContextHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private ApplicationContext context;

    public SpringContextHelper(Application application) {
        ServletContext servletContext = ((WebApplicationContext) application.getContext()).getHttpSession().getServletContext();
        context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    }

    public Object getBean(final String beanRef) {
        return context.getBean(beanRef);
    }
    
    public Object getBean(final String beanRef,Object parameter) {
        return context.getBean(beanRef,parameter);
    }    

}