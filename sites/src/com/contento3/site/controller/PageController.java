package com.contento3.site.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.thymeleaf.TemplateEngine;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.site.page.PageResourceUtil;
import com.contento3.util.DomainUtil;

/**
 * Handles request from the browser
 * to display the correct page of the site.
 * @author hammad.afridi
 *
 */
@Controller
public class PageController {

	private static final Logger LOGGER = Logger.getLogger(PageController.class);

	private TemplateEngine templateEngine;
	
	private PageService pageService;
	
	private SiteService siteService; 
	
    @RequestMapping(method = RequestMethod.GET)
	protected ModelAndView handleRequestInternal(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {

		String requestURI = request.getRequestURI();
    	LOGGER.debug("Page Controller for request uri: "+request.getRequestURI());

		ModelAndView modelAndView = new ModelAndView();
		if (!requestURI.equals("/favicon.ico")){
			modelAndView = new ModelAndView();
		}

		
		SiteDto site = (SiteDto) request.getAttribute("site");
		
		if (site.getSiteId()==null){
			site = fetchSite(request);
		}
		
		modelAndView.getModel().put("site", site);
		
		PageDto page = fetchPage(requestURI,site.getSiteId());
		
		if (page!=null){
			modelAndView.getModel().put("page", page);
		}
		
		modelAndView.setViewName(requestURI);
		return modelAndView;
	}

	private PageDto fetchPage(String requestURI,final Integer siteId){
		
		String resourceType = PageResourceUtil.fetchTemplateResourceType(requestURI);
		String pagePath = "";

		if (resourceType.equals(PageResourceUtil.PAGE)){
			String[] pageUri = requestURI.split("page/");
			if (requestURI.contains("page/")) {
				pagePath = pageUri[1];
			} else {
				pagePath = pageUri[0];
				
				//Split it further to see for any category etc in the url
				pagePath = pagePath.split("/")[1];
			}

		    pagePath = pagePath.startsWith("/")? pagePath : String.format("/%s",pagePath);

			PageDto page;
			try {
				page = pageService.findByPathForSite(pagePath, siteId);
			} catch (final PageNotFoundException e) {
				return null;
			}
			return page;
		}
		return null;
	}
	
	private SiteDto fetchSite(HttpServletRequest request){
		String domainName = DomainUtil.fetchDomain(request);
	    SiteDto site = null;
	    if(request.getParameter("siteId")!= null){
			site = siteService.findSiteById(Integer.parseInt(request.getParameter("siteId")));
	    }
	    else if( !domainName.equals("localhost")){
			site = siteService.findSiteByDomain(domainName, true);
	    }
	    
	    return site;
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
