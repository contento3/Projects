package com.contento3.site.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.contento3.caching.filter.CachingFilter;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.DomainUtil;

public class SiteInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = Logger.getLogger(CachingFilter.class);


	static Logger logger = Logger.getLogger(SiteInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
	logger.info("Before handling the request");
	
    final ServletContext servletContext  = request.getServletContext();
    final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    final SiteService siteService = (SiteService)context.getBean("siteService");
    
    SiteDto site = siteService.findSiteByDomain(DomainUtil.fetchDomain(request));
    request.setAttribute("site", site);
	return super.preHandle(request, response, handler);
	}
	 

}
