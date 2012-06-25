package com.contento3.site.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.AbstractTemplateView;

//import com.contento3.site.registration.model.User;



/**
 * Handles request from the browser
 * to display the correct page of the site.
 * @author hammad.afridi
 *
 */
public class PageController extends AbstractController {

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

	public void setFreeMarkerView(final AbstractTemplateView freemarkerView){
		this.freemarkerView = freemarkerView;
	} 
}
