package com.contento3.site.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.ResourceNotFoundException;

public class StaticResourceViewResolver extends AbstractView {
	private static final Logger LOGGER = Logger.getLogger(StaticResourceViewResolver.class);
		
	private TemplateService templateService;
	
//	private ImageService imageService;
	
	
	@Override
	protected void renderMergedOutputModel(Map arg0, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			TemplateDto templateDto = templateService.findTemplateByPathAndAccount(request.getRequestURI(),new Integer(1));
			response.getWriter().print(templateDto.getTemplateText());
		}
		catch(ResourceNotFoundException rnfe){
			LOGGER.warn(String.format("Unable to fin resource for with path [%s]",request.getRequestURI()));
		}
		
		response.getWriter().close();
	}
	
	public void setTemplateService(final TemplateService templateService){
		this.templateService = templateService;
	}

}