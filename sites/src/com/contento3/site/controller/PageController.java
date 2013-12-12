package com.contento3.site.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.contento3.cms.site.structure.dto.SiteDto;

//import com.contento3.site.registration.model.User;



/**
 * Handles request from the browser
 * to display the correct page of the site.
 * @author hammad.afridi
 *
 */
public class PageController extends AbstractController {

	private static final Logger LOGGER = Logger.getLogger(PageController.class);

	@Override
	protected ModelAndView handleRequestInternal(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		String requestURI = request.getRequestURI();
    	LOGGER.debug("Page Controller for request uri: "+request.getRequestURI());

		ModelAndView modelAndView = new ModelAndView();
		if (!requestURI.equals("/favicon.ico")){
			modelAndView = new ModelAndView();
		}
	
		final SiteDto site = (SiteDto) request.getAttribute("site");
		modelAndView.getModel().put("site", site);
		modelAndView.setViewName(requestURI);
		return modelAndView;
	}

}
