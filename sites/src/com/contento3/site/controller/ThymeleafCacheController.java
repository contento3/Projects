package com.contento3.site.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.thymeleaf.TemplateEngine;

import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.service.SiteService;

/**
 * Handles request from the browser
 * to display the correct page of the site.
 * @author hammad.afridi
 *
 */
@Controller
@RequestMapping("/cacheClear")
public class ThymeleafCacheController {

	private static final Logger LOGGER = Logger.getLogger(ThymeleafCacheController.class);

	@Resource
	private TemplateEngine templateEngine;
	
	@Resource
	private PageService pageService;
	
	@Resource
	private SiteService siteService; 
	
	@RequestMapping(method = RequestMethod.GET)
	@ResponseStatus(value = HttpStatus.OK) 
	public void clearCache(@RequestParam("template") final String template){
		if (template!=null){
			templateEngine.clearTemplateCacheFor(template);
			LOGGER.debug("Tried to clear cache for template ["+ template+"]");
		}
	}

	public void setPageService (final PageService pageService){
		this.pageService = pageService;
	}

	public void setSiteService (final SiteService siteService){
		this.siteService = siteService;
	}

	public void setTemplateEngine (final TemplateEngine templateEngine){
		this.templateEngine = templateEngine;
	}

}


