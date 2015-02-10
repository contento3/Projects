package com.contento3.thymeleaf.dialect.slider;

import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.service.ImageService;

/**
 * SliderTemplateHelper class 
 * This class is useful for using Java methods in Thymeleaf templates
 * 
 * @author Yawar
 *
 */
public class SliderTemplateHelper {
    
	/**
	 * imageService
	 */
	ImageService imageService ;
   
	/**
	 * 
	 * @param accountId
	 * @param siteId
	 * @param libraryId
	 * @return
	 */
	public Collection <ImageDto> findSliderImagesByLibraryId(final Integer accountId, final Integer siteId, final Integer libraryId) {
		if(accountId.equals(null)){
		 return null;	
		}
		Collection<ImageDto> imageList = imageService.findImagesByLibraryAndAccountId(libraryId, accountId);
		return imageList;
	}
	
	/**
	 * 
	 * @param accountId
	 * @param siteId
	 * @param catId
	 * @return
	 */
	public Integer getImagesCount(final Integer accountId, final Integer siteId, final Integer libraryId) {
			Collection<ImageDto> imageList = findSliderImagesByLibraryId(accountId, siteId, libraryId);
		final Integer totalArticle = imageList.size();
		return totalArticle;
	}
	
	/**
	 * 
	 * SliderTemplateHelper constructor 
	 */
	public SliderTemplateHelper() {
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		imageService = (ImageService) ctx.getBean("imageService");
	}
    
}