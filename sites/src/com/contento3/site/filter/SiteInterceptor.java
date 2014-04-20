package com.contento3.site.filter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.cache.StandardCacheManager;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.DomainUtil;

public class SiteInterceptor extends HandlerInterceptorAdapter {

	static Logger logger = Logger.getLogger(SiteInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
	logger.info("Before handling the request");
		
	//	if (null==request.getAttribute("site")){
		    final ServletContext servletContext  = request.getServletContext();
		    final WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
		    final SiteService siteService = (SiteService)context.getBean("siteService");
		    final PageService pageService = (PageService)context.getBean("pageService");
		    
		    //TODO Change to a system user
        	final UsernamePasswordToken token = new UsernamePasswordToken("guest123","guest123");
		    final Subject subject =   SecurityUtils.getSubject();
		    if (!subject.isAuthenticated()){
		    	subject.login(token);
		    }
		    
		    SiteDto site=null;
		    String domainName = DomainUtil.fetchDomain(request);
		    
		    if(request.getParameter("siteId")!= null){
				site = siteService.findSiteById(Integer.parseInt(request.getParameter("siteId")));
		    }
		    else if( !domainName.equals("localhost")){
				site = siteService.findSiteByDomain(domainName, true);
		    }
		    
		    request.setAttribute("site", site);
		    
	//	}
		
	return super.preHandle(request, response, handler);
	}

}
