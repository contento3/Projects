package com.contento3.site.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.contento3.site.resolver.FreemarkerViewResolver;
import com.contento3.site.resolver.StaticResourceViewResolver;

/**
 * Used to return js,css,image from the database
 * @author HAMMAD
 *
 */
public class StaticResourceController extends AbstractController {

	private StaticResourceViewResolver view;
	
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView modelAndView = null;
		
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
