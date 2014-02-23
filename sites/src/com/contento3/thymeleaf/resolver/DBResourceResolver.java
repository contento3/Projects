package com.contento3.thymeleaf.resolver;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContext;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.util.Validate;

import com.contento3.cms.page.dto.PageDto;
import com.contento3.cms.page.service.PageService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.site.template.dto.TemplateContentDto;
import com.contento3.site.template.loader.TemplateLoader;

public class DBResourceResolver implements IResourceResolver {

private static final Logger logger = LoggerFactory.getLogger(DBResourceResolver.class);

public static final String NAME = "DB";

private TemplateLoader templateLoader;

private PageService pageService;

public DBResourceResolver() {
    super();
}

public DBResourceResolver(final TemplateLoader templateLoader,final PageService pageService) {
    super();
    this.templateLoader = templateLoader;
    this.pageService = pageService;
}

public String getName() {
    return NAME; 
}


public InputStream getResourceAsStream(final TemplateProcessingParameters templateProcessingParameters, String resourceName) {
    Validate.notNull(resourceName, "Resource name cannot be null");
    TemplateContentDto dto = null;

    final RequestContext requestContext = (RequestContext)templateProcessingParameters.getContext().getVariables().get("springRequestContext");
	final Map<String,Object> map = requestContext.getModel();
	final SiteDto site = (SiteDto)map.get("site");
	Validate.notNull(site, "site cannot be null");
	
    try {

    	if(site.getSiteId() == null) {
    		 return new ByteArrayInputStream(new String("Site not found").getBytes());
    	}
    	
    	
    	//Look for site default page if the reource name starts with "/"
    	if (resourceName.equals("/")){
    		final PageDto pageDto = pageService.findById(site.getDefaultPageId());
    		resourceName = pageDto.getUri();
    	}

    	final Integer siteId = site.getSiteId();
    	dto = templateLoader.load(resourceName, siteId);

    	if (null==dto){
    		dto = templateLoader.loadErrorTemplate("SIMPLE", resourceName,siteId);
    	}
   	
        return 	new ByteArrayInputStream(dto.getContent().getBytes());

    } catch (final Exception e) {
        if (logger.isDebugEnabled()) {
            if (logger.isTraceEnabled()) {
                logger.trace(
                        String.format(
                                "[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as " +
                                "maybe this resource is not intended to be resolved by this resolver. " +
                                "Exception is provided for tracing purposes: ", 
                                TemplateEngine.threadIndex(), templateProcessingParameters.getTemplateName(),
                                resourceName),
                        e);
            } else {
                logger.debug(
                        String.format(
                                "[THYMELEAF][%s][%s] Resource \"%s\" could not be resolved. This can be normal as " +
                                "maybe this resource is not intended to be resolved by this resolver. " +
                                "Exception message is provided: %s: %s", 
                                TemplateEngine.threadIndex(), templateProcessingParameters.getTemplateName(),
                                resourceName, e.getClass().getName(), e.getMessage()));
            }
        }
        return null;
    }
}

}