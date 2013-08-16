package com.contento3.web.content.article.listener;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.common.dto.Dto;
import com.contento3.dam.image.service.ImageService;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;

public class ContentScopeChangeListener implements ValueChangeListener {

	private static final long serialVersionUID = 1L;

	private Integer articleId;
	
	private Collection <Dto> dtos;
	
	private Collection <Dto> assignedDtos;
	
	private ArticleImageService articleImageService;

	private ImageService imageService;

	/**
	 * Used to get service beans from spring context.
	 */
	private SpringContextHelper helper;

	private GenricEntityPicker genricEntityPicker;
	
	private Integer accountId;
	
	public ContentScopeChangeListener(final Integer articleId,final Integer accountId,final SpringContextHelper helper,final GenricEntityPicker imagePicker) {
		this.helper = helper;
		articleImageService = (ArticleImageService) helper.getBean("articleImageService");
		imageService = (ImageService) helper.getBean("imageService");

		this.genricEntityPicker = imagePicker;
		this.articleId = articleId;
		this.accountId = accountId;
	}

	@Override
	public void valueChange(final ValueChangeEvent event) {
		Integer value = (Integer)event.getProperty().getValue();
		if (value!=null){
			
			if (CollectionUtils.isEmpty(dtos))
				dtos = (Collection) imageService.findImageByAccountId(accountId);

			assignedDtos = (Collection<Dto>) populateImageDtos(articleImageService.findAsscArticleImageByArticleIdAndScopeId(this.articleId,value));
			genricEntityPicker.rebuildWithNewAssignedDtos(assignedDtos,dtos);
		}
		else {
			genricEntityPicker.rebuildWithNewAssignedDtos(null,null);
		}
	}

	private Collection <Dto> populateImageDtos(Collection <ArticleImageDto> articleImageDtos){
		Collection <Dto> dtos = new ArrayList<Dto>();
		Dto dto;
		for (ArticleImageDto articleImageDto : articleImageDtos){
			dto = articleImageDto.getImage();
			dtos.add(dto);
		}
		return dtos;
	}


}
