package com.contento3.site.register.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.AbstractTemplateView;

/**
 * Handles http request from the browser
 * to register a user for a site.
 * @author hammad.afridi
 *
 */
public class RegistrationController extends AbstractController {

	private AbstractTemplateView freemarkerView;
		
	@Override
	protected ModelAndView handleRequestInternal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ModelAndView modelAndView = null;
		String requestURI = request.getRequestURI();
		if (!requestURI.equals("/favicon.ico")){
			modelAndView = new ModelAndView();
			modelAndView.setView(freemarkerView); 
		}

		return modelAndView;
	}

}
