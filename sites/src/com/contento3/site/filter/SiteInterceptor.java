package com.contento3.site.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.DomainUtil;

public class SiteInterceptor extends HandlerInterceptorAdapter {

	static Logger logger = Logger.getLogger(SiteInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
	logger.info("Before handling the request");
		
		if (null==request.getAttribute("site")){
		    final ServletContext servletContext  = request.getServletContext();
		    final WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		    final SiteService siteService = (SiteService)context.getBean("siteService");
			
		    SiteDto site=null;
		    String domainName = DomainUtil.fetchDomain(request);
		    if( !domainName.equals("localhost") ){
				site = siteService.findSiteByDomain(domainName);
		    }
		    else if(request.getParameter("siteId")!= null){
				site = siteService.findSiteById( Integer.parseInt(request.getParameter("siteId")));
		    }
		
		    request.setAttribute("site", site);
		}
		
	return super.preHandle(request, response, handler);
	}

}
