package com.contento3.site.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.contento3.site.resolver.StaticResourceViewResolver;

/**
 * Used to return js,css,image from the database
 * @author HAMMAD
 *
 */
public class StaticResourceController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(PageController.class);

	private StaticResourceViewResolver view;
	
	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		ModelAndView modelAndView = null;
		
		LOGGER.info("Static Resource controller");
		if (!request.getRequestURI().equals("/favicon.ico")){
		 modelAndView = new ModelAndView();
			modelAndView.setView(view); 
		}

		return modelAndView;
	}

	public void setStaticResourceViewResolver(final StaticResourceViewResolver view){
		this.view = view;
	} 
}
