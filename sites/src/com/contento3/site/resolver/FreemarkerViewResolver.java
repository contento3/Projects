package com.contento3.site.resolver;

import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.site.template.render.engine.RenderingEngine;

public class FreemarkerViewResolver extends AbstractView {
	private static final Logger LOGGER = Logger.getLogger(FreemarkerViewResolver.class);
		
	private RenderingEngine freemarkerRenderingEngine;

	private SiteService siteService;
	
	@Override
	protected void renderMergedOutputModel(Map arg0, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String requestURI = request.getRequestURI();
		String[] pageUri = requestURI.split("/page/");
		String pagePath ="";
		
		if (requestURI.contains("page/")){
			pagePath = pageUri[1];
		}
		else {
			pagePath = pageUri[0];
		}
			
		SiteDto siteDto = siteService.findSiteByDomain(fetchDomain(request));	
		PrintWriter writer = response.getWriter();

		// Read the data file and process the template using FreeMarker
		try {
			freemarkerRenderingEngine.process(siteDto,pagePath,writer);
		}
		catch(Exception e) {
			LOGGER.error(String.format("Something went wrong while accessing the page [%s:] [%s] ",pagePath,e.getLocalizedMessage()));
			//TODO Redirect to the error page is required.
		}
		finally{
			writer.close();
		}
	}
	
	public void setFreemarkerRenderingEngine(final RenderingEngine freemarkerRenderingEngine ){
		this.freemarkerRenderingEngine = freemarkerRenderingEngine;
	}

	public void setSiteService(final SiteService siteService){
		this.siteService = siteService;
	}

	private String fetchDomain(HttpServletRequest request) throws MalformedURLException {
		URL url = new URL(request.getRequestURL().toString());
		return url.getHost();
	}

}
