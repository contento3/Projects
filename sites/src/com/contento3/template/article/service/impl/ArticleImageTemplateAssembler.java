package com.contento3.template.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.template.image.service.ImageTemplateAssembler;

public class ArticleImageTemplateAssembler {

	private ImageTemplateAssembler imageAssembler;
	
	/**
	 * Assemble a {@link ArticleImageDto} we get from core services 
	 * into freemarker template dto {@link ArticleImageTemplateDto} for Article
	 * @param articleImagetemplaetDto
	 * @return ArticleImageTemplateDto
	 */
	public ArticleImageTemplateDto assemble(final ArticleImageDto articleImageDto){
		final ArticleImageTemplateDto dto = new ArticleImageTemplateDto();
		dto.setImage(imageAssembler.assemble(articleImageDto.getImage()));
		dto.setArticleId(articleImageDto.getArticle().getId());
		dto.setContentScope(articleImageDto.getContentScope().getScope());
		return dto;
	}

	/**
	 * Assemble a {@link Collection} of {@link ArticleImageDto} we get from core services 
	 * into freemarker template dto {@link ArticleImageTemplateDto} for Article
	 * @param articleImageDtos
	 * @return Collection<ArticleImageTemplateDto>
	 */
	public Collection<ArticleImageTemplateDto> assemble(final Collection <ArticleImageDto> articleImageDtos){
		final Collection <ArticleImageTemplateDto> dtos = new ArrayList<ArticleImageTemplateDto>();
		for (ArticleImageDto artlImageDto : articleImageDtos){
			dtos.add(assemble(artlImageDto));
		}
		return dtos;
	}

}
