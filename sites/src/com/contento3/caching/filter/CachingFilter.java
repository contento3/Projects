package com.contento3.caching.filter;

import java.net.MalformedURLException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import net.sf.ehcache.constructs.web.filter.SimplePageCachingFilter;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.util.DomainUtil;


public class CachingFilter extends SimplePageCachingFilter {

	private static final Logger LOGGER = Logger.getLogger(CachingFilter.class);

	@Override
	protected String calculateKey(final HttpServletRequest httpRequest) {  
		final StringBuffer keyBuffer = new StringBuffer();  
		keyBuffer.  
	    append(httpRequest.getMethod()).  
	    append(httpRequest.getRequestURI());
		
	    SiteDto site;
        final ServletContext servletContext  = httpRequest.getServletContext();
        final ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(servletContext);
	    final SiteService siteService = (SiteService)context.getBean("siteService");
        
	    try {
	    	site = siteService.findSiteByDomain(DomainUtil.fetchDomain(httpRequest));
		} catch (Exception e) {
			LOGGER.error("Invalid request",e);
			throw new IllegalArgumentException(e);
		}
		keyBuffer.append(String.format("?siteId=%d",site.getSiteId()));
	    String key = keyBuffer.toString();
	    LOGGER.info(String.format("CachingFilter looking for page from %s",httpRequest.getRequestURI()));
	    return key;  
	}  

}
