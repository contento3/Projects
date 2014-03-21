package com.contento3.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.contento3.cms.page.exception.PageNotFoundException;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.DomainUtil;

public class DefaultPageFilter implements Filter {

	private static final Logger LOGGER = Logger.getLogger(DefaultPageFilter.class);
	
	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
        final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
	    final SiteService siteService = (SiteService)context.getBean("siteService");
	    final PageService pageService = (PageService)context.getBean("pageService");
	    
    	final UsernamePasswordToken token = new UsernamePasswordToken("guest123","guest123");
	    final Subject subject =   SecurityUtils.getSubject();
	    if (!subject.isAuthenticated()){
	    	subject.login(token);
	    }

	    SiteDto site=null;
	    final String domainName = DomainUtil.fetchDomain((HttpServletRequest)request);
	    if( !domainName.equals("localhost") ){
			site = siteService.findSiteByDomain(domainName, true);
	    }
	    else if(request.getParameter("siteId")!= null){
			site = siteService.findSiteById( Integer.parseInt(request.getParameter("siteId")));
	    }

	    String requestURI = ((HttpServletRequest)request).getRequestURI();
	    request.setAttribute("site", site);
	    if (requestURI.equals("/")){
	    	try {
	    		final Integer defaultPageId = site.getDefaultPageId();
	    		if (null!=defaultPageId)
				request.getServletContext().getRequestDispatcher(pageService.findById(defaultPageId).getUri()).forward(request, response); 
				
			} catch (final PageNotFoundException e) {
				LOGGER.warn("No default page found for site with id ["+site.getSiteId()+"]");
			}
	    }
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
