package com.contento3.cms.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.article.service.ArticleAssembler;
import com.contento3.cms.site.structure.service.SiteAssembler;

public class ArticleAssemblerImpl implements ArticleAssembler {
	/**
	 * Transform Stie to siteDto and vice versa.
	 */
	private SiteAssembler siteAssembler;
	/**
	 * Transform Account to AccountDto and vice versa.
	 */
	private AccountAssembler accountAssembler;
	
	public ArticleAssemblerImpl(final SiteAssembler siteAssembler,final AccountAssembler accountAssembler ) {
		this.siteAssembler = siteAssembler;
		this.accountAssembler = accountAssembler;
		
	}
	

	@Override
	public Article dtoToDomain(ArticleDto dto) {
		
		Article domain = new Article();
		domain.setArticleId(dto.getArticleId());
		domain.setHead(dto.getHead());
		domain.setBody(dto.getBody());
		domain.setTeaser(dto.getTeaser());
		domain.setDateCreated(dto.getDateCreated());
		domain.setDatePosted(dto.getDatePosted());
		domain.setLastUpdated(dto.getLastUpdated());
		domain.setExpiryDate(dto.getExpiryDate());
		domain.setIsVisible(dto.getIsVisible());
		domain.setSite(siteAssembler.dtosToDomains(dto.getSite()));
		domain.setAccount(accountAssembler.dtoToDomain(dto.getAccount()));
		return domain;
	}

	@Override
	public ArticleDto domainToDto(Article domain) {
		ArticleDto dto = new ArticleDto();
		dto.setArticleId(domain.getArticleId());
		dto.setUuid(domain.getUuid());
		dto.setHead(domain.getHead());
		dto.setBody(domain.getBody());
		dto.setTeaser(domain.getTeaser());
		dto.setDateCreated(domain.getDateCreated());
		dto.setDatePosted(domain.getDatePosted());
		dto.setLastUpdated(domain.getLastUpdated());
		dto.setExpiryDate(domain.getExpiryDate());
		dto.setIsVisible(domain.getIsVisible());
		dto.setSite(siteAssembler.domainsToDtos(domain.getSite()));
		dto.setAccount(accountAssembler.domainToDto(domain.getAccount()));
		return dto;
	
	}

	@Override
	public Collection<ArticleDto> domainsToDtos(Collection<Article> domains) {
		Collection<ArticleDto> dtos = new ArrayList<ArticleDto>();
		for (Article domain : domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	@Override
	public Collection<Article> dtosToDomains(Collection<ArticleDto> dtos) {
		Collection<Article>	domains = new ArrayList<Article>();
		for(ArticleDto dto : dtos){
			domains.add(dtoToDomain(dto));
		}
		return domains;
	}

}
