package com.contento3.site.filter;

import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

import com.contento3.caching.filter.CachingFilter;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.site.template.loader.TemplateLoader;
import com.contento3.util.DomainUtil;

import edu.emory.mathcs.backport.java.util.TreeSet;

public class SiteInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = Logger.getLogger(CachingFilter.class);


	static Logger logger = Logger.getLogger(SiteInterceptor.class);


	private TemplateResolver templateResolver;
	//private TemplateResolver templateResolver;


	private TemplateEngine templateEngine;
	
	@Override
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response, Object handler) throws Exception {
	logger.info("Before handling the request");
	
    final ServletContext servletContext  = request.getServletContext();
    final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
    final SiteService siteService = (SiteService)context.getBean("siteService");
	
    if(request.getParameter("clearcache")!= null){
    	templateEngine.clearTemplateCacheFor("/page/aboutus");
    }
//    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    /*InputStream stream = classLoader.getResourceAsStream("debuggingDomains.properties");
    if (stream == null) {
        // File not nound
      } else {
        Properties p = new Properties();
        p.load(stream);
      }
*/
    SiteDto site =null;//= siteService.findSiteByDomain(DomainUtil.fetchDomain(request));
    String domainName = DomainUtil.fetchDomain(request);
    if( !domainName.equals("localhost") )
		site = siteService.findSiteByDomain(
			domainName
			);
	else if(request.getParameter("siteId")!= null)	    		
		site = siteService.findSiteById( Integer.parseInt(request.getParameter("siteId"))
    			);
    request.setAttribute("site", site);
	return super.preHandle(request, response, handler);
	}
	public void setTemplateResolver(final TemplateResolver templateResolver){
		this.templateResolver = templateResolver;
	}
	public void setTemplateEngine(final TemplateEngine templateEngine){
		this.templateEngine = templateEngine;
	}

}
