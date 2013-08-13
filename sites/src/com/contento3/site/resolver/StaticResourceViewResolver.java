package com.contento3.site.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.cms.site.structure.service.SiteService;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.util.DomainUtil;

public class StaticResourceViewResolver extends AbstractView {
	private static final Logger LOGGER = Logger.getLogger(StaticResourceViewResolver.class);
	
	/**
	 * Services for template
	 */
	private TemplateService templateService;
	
	/**
	 * Service for image
	 */
	private ImageService imageService;
	
	/**
	 * Service for site.
	 */
	private SiteService siteService;
	
	@Override
	protected void renderMergedOutputModel(final Map arg0, final HttpServletRequest request,
			final HttpServletResponse response) {
		
		final String requestURI = request.getRequestURI();
		final String[] pageUri = requestURI.split("/image/");
		String resourcePath ="";
		
	    final String siteDomain = DomainUtil.fetchDomain(request);
	    final SiteDto site = siteService.findSiteByDomain(siteDomain);
	    final AccountDto accountDto;

	    if (null!=site){
	    	 accountDto = site.getAccountDto();
	    	 try {
	    		 if (requestURI.contains("image/")){
	    			 resourcePath = pageUri[1];
					 ImageDto imageDto = imageService.findImageByNameAndAccountId(resourcePath,accountDto.getAccountId());
					
					 if (null==imageDto){
						LOGGER.warn(String.format("Unable to find resource for path [%s]",resourcePath));
					 }
					 else {
						 response.getOutputStream().write(imageDto.getImage());
						 response.getOutputStream().close();
					 }
				}
				else {
						final TemplateDto templateDto = templateService.findTemplateByPathAndAccount(request.getRequestURI(),accountDto.getAccountId());
						response.getWriter().print(templateDto.getTemplateText());
						response.getWriter().close();
				}
	    	 }	
	    	 catch(EntityNotFoundException rnfe){
				LOGGER.warn(String.format("Unable to find resource for path [%s]",request.getRequestURI()));
	    	 }
	    	 catch(Exception rnfe){
					LOGGER.warn(String.format("Unable to find resource for path [%s]",request.getRequestURI()));
		     }
	    }
	    else {
	    	LOGGER.error(String.format("Unable to find accountId for site url [%s] while fetching the resource with path [%s]",siteDomain,request.getRequestURI()));
	    }
	}
	
	public void setTemplateService(final TemplateService templateService){
		this.templateService = templateService;
	}

	public void setImageService(final ImageService imageService){
		this.imageService = imageService;
	}

	public void setSiteService(final SiteService siteService){
		this.siteService = siteService;
	}
	
}