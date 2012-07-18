package com.contento3.cms.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.article.dto.RelatedArticleDto;
import com.contento3.cms.article.model.RelatedArticle;
import com.contento3.cms.article.service.ArticleAssembler;
import com.contento3.cms.article.service.RelatedArticleAssembler;

public class RelatedArticleAssemblerImpl implements RelatedArticleAssembler {

	private ArticleAssembler articleAssembler;
	
	public RelatedArticleAssemblerImpl(final ArticleAssembler articleAssembler) {
		this.articleAssembler = articleAssembler;
	}
	@Override
	public RelatedArticle dtoToDomain(RelatedArticleDto dto) {
		RelatedArticle domain = new RelatedArticle();
		domain.setArticle(articleAssembler.dtoToDomain(dto.getArticle()));
		domain.setType(dto.getType());
		domain.setPrimaryKey(dto.getPrimaryKey());
		return domain;
	}

	@Override
	public RelatedArticleDto domainToDto(RelatedArticle domain) {
		RelatedArticleDto dto = new RelatedArticleDto();
		dto.setArticle(articleAssembler.domainToDto(domain.getArticle()));
		dto.setType(domain.getType());
		dto.setPrimaryKey(domain.getPrimaryKey());
		return dto;
	}

	@Override
	public Collection<RelatedArticleDto> domainsToDtos(
			Collection<RelatedArticle> domains) {
		Collection<RelatedArticleDto> dtos = new ArrayList<RelatedArticleDto>();
		for(RelatedArticle domain : domains){
			dtos.add(domainToDto(domain));
		}
		
		return dtos;
	}

	@Override
	public Collection<RelatedArticle> dtosToDomains(
			Collection<RelatedArticleDto> dtos) {
		Collection<RelatedArticle> domains = new ArrayList<RelatedArticle>();
		for(RelatedArticleDto dto : dtos){
			domains.add(dtoToDomain(dto));
		}
		return domains;
	}

	

}
