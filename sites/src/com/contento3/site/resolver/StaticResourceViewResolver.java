package com.contento3.site.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.view.AbstractView;

import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.common.exception.ResourceNotFoundException;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;

public class StaticResourceViewResolver extends AbstractView {
	private static final Logger LOGGER = Logger.getLogger(StaticResourceViewResolver.class);
	
	
	private TemplateService templateService;
	
	private ImageService imageService;
	
	
	@Override
	protected void renderMergedOutputModel(Map arg0, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String requestURI = request.getRequestURI();
		String[] pageUri = requestURI.split("/image/");
		String resourcePath ="";
		
		if (requestURI.contains("image/")){
			resourcePath = pageUri[1];
			ImageDto imageDto = imageService.findImageByNameAndAccountId(resourcePath,new Integer(1));
			response.getOutputStream().write(imageDto.getImage());
		}
		else {
			try {
				TemplateDto templateDto = templateService.findTemplateByPathAndAccount(request.getRequestURI(),new Integer(1));
				response.getWriter().print(templateDto.getTemplateText());
			}
			catch(ResourceNotFoundException rnfe){
				LOGGER.warn(String.format("Unable to find resource for path [%s]",request.getRequestURI()));
			}
		}
		response.getWriter().close();
	}
	
	public void setTemplateService(final TemplateService templateService){
		this.templateService = templateService;
	}

	public void setImageService(final ImageService imageService){
		this.imageService = imageService;
	}

}