package com.contento3.site.resolver;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
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
import com.mchange.lang.ArrayUtils;
import com.mysql.jdbc.Util;

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
		
		//Just in case jsession is appended by thymeleaf.Remove it first
		final String[] jsessionIdSplit = requestURI.split(";");
		
		final String[] pageUri = jsessionIdSplit[0].split("/image/");
		String resourcePath ="";
		
	    final String siteDomain = DomainUtil.fetchDomain(request);
	    final SiteDto site = siteService.findSiteByDomain(siteDomain, true);
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
						 byte[] image = imageDto.getImage();
						 String[] widthAndHeightArray = null;
						 
						 if (null!=request.getParameter("size"))
						 {
							 widthAndHeightArray = request.getParameter("size").split("_");
						 }
							
						 try {	
							 if(widthAndHeightArray!=null && widthAndHeightArray.length > 0){
						
								 Integer width = new Integer(widthAndHeightArray[0]);
								 BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
								 if(widthAndHeightArray.length > 1){
									 Integer height = new Integer(widthAndHeightArray[1]);
									 bufferedImage = Scalr.resize(bufferedImage,width,height);
								 }
								 else {
									 bufferedImage = Scalr.resize(bufferedImage,width);
								 }
								
								 final ByteArrayOutputStream os = new ByteArrayOutputStream();
								 ImageIO.write(bufferedImage, "gif", os);	
								 os.flush();
								 image = os.toByteArray();
								 os.close();
							 	}
							 } catch (Exception e) {
							 // TODO: handle exception
							 }
						 response.getOutputStream().write(image);
						 response.getOutputStream().close();
					 }
	    		 }
				else {
						final TemplateDto templateDto = templateService.findTemplateByNameAndAccount(request.getRequestURI(),accountDto.getAccountId());
			    		if (requestURI.contains("js/")){
			    			 response.setHeader("Content-type", "application/javascript");
			    		}	 
			    		else if (requestURI.contains("css/")){
			    			 response.setHeader("Content-type", "text/css");
			    		}	
			    		
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