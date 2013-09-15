package com.contento3.template.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.util.CollectionUtils;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.template.article.dto.ArticleTemplateDto;

public class ArticleTemplateAssembler {

	private ArticleImageTemplateAssembler articleImageAssembler;
	
	/**
	 * Assemble a {@link ArticleDto} we get from core services 
	 * into freemarker template dto {@link ArticleTemplateDto} for Article
	 * @param articleDto
	 * @return ArticleTemplateDto
	 */
	public ArticleTemplateDto assemble(final ArticleDto articleDto){
		final ArticleTemplateDto dto = new ArticleTemplateDto();
		dto.setArticleId(articleDto.getId());
		dto.setBody(articleDto.getBody());
		dto.setDateCreated(articleDto.getDateCreated());
		dto.setDatePosted(articleDto.getDatePosted());
		dto.setHead(articleDto.getHead());
		dto.setTeaser(articleDto.getTeaser());
		dto.setExpiryDate(articleDto.getExpiryDate());
		
		if (!CollectionUtils.isEmpty(articleDto.getAssociateImagesDtos())){
			articleImageAssembler.assemble(articleDto.getAssociateImagesDtos());
		}
		return dto;
	}
	
	/**
	 * Assemble a {@link Collection} of {@link ArticleDto} we get from core services 
	 * into freemarker template dto {@link ArticleTemplateDto} for Article
	 * @param articleDtos
	 * @return Collection<ArticleTemplateDto>
	 */
	public Collection<ArticleTemplateDto> assemble(final Collection <ArticleDto> articleDtos){
		final Collection <ArticleTemplateDto> dtos = new ArrayList<ArticleTemplateDto>();
		for (ArticleDto article : articleDtos){
			dtos.add(assemble(article));
		}
		return dtos;
	}
}
