package com.contento3.cms.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.model.ArticleImage;
import com.contento3.cms.article.model.ArticleImageLinkPK;
import com.contento3.cms.article.service.ArticleAssembler;
import com.contento3.cms.article.service.ArticleImageAssembler;
import com.contento3.cms.content.service.AssociatedContentScopeAssembler;
import com.contento3.dam.image.service.ImageAssembler;

public class ArticleImageAssemblerImpl implements ArticleImageAssembler {

	private ArticleAssembler articleAssembler;
	private ImageAssembler imageAssembler;
	private AccountAssembler accountAssembler;
	private AssociatedContentScopeAssembler contentScopeAssembler;

	/**
	 * Constructor
	 * @param articleAssembler
	 * @param imageAssembler
	 * @param contentScopeAssembler
	 * @param accountAssembler
	 */
	public ArticleImageAssemblerImpl(final ArticleAssembler articleAssembler,final ImageAssembler imageAssembler,final AssociatedContentScopeAssembler contentScopeAssembler,final AccountAssembler accountAssembler) {
		this.articleAssembler = articleAssembler;
		this.imageAssembler = imageAssembler;
		this.contentScopeAssembler= contentScopeAssembler;
		this.accountAssembler = accountAssembler;
	}
	
	/**
	 * ArtilceImageDto to ArticleImage conversion
	 */
	@Override
	public ArticleImage dtoToDomain(final ArticleImageDto dto) {
		Validate.notNull(dto,"article dto cannot be null");

		final ArticleImage domain = new ArticleImage();
		final ArticleImageLinkPK pk = new ArticleImageLinkPK();
		pk.setArticle(this.articleAssembler.dtoToDomain(dto.getArticle()));
		pk.setImage(this.imageAssembler.dtoToDomain(dto.getImage()));
		pk.setContentScope(this.contentScopeAssembler.dtoToDomain(dto.getContentScope()));
		domain.setPrimaryKey(pk);
		domain.setAccount(this.accountAssembler.dtoToDomain(dto.getAccount()));
		return domain;
	}

	/**
	 * ArtilceImage to ArticleImageDto conversion
	 */
	@Override
	public ArticleImageDto domainToDto(final ArticleImage domain) {
		Validate.notNull(domain,"article domain cannot be null");

		final ArticleImageDto dto = new ArticleImageDto();
		dto.setArticle(this.articleAssembler.domainToDto(domain.getPrimaryKey().getArticle()));
		dto.setImage(this.imageAssembler.domainToDto(domain.getPrimaryKey().getImage()));
		dto.setContentScope(this.contentScopeAssembler.domainToDto(domain.getPrimaryKey().getContentScope()));
		dto.setAccount(this.accountAssembler.domainToDto(domain.getAccount()));
		return dto;
	}

	/**
	 * Domains to Dtos Conversion
	 */
	@Override
	public Collection<ArticleImageDto> domainsToDtos(final Collection<ArticleImage> domains) {
		Validate.notNull(domains,"article domains cannot be null");

		final Collection<ArticleImageDto> dtos = new ArrayList<ArticleImageDto>();
		for(ArticleImage domain : domains){
			dtos.add(domainToDto(domain));
		}
		return dtos;
	}

	/**
	 * Dtos to Domains conversion
	 */
	@Override
	public Collection<ArticleImage> dtosToDomains(final Collection<ArticleImageDto> dtos) {
		Validate.notNull(dtos,"article dtos collection cannot be null");

		final Collection<ArticleImage> domains = new ArrayList<ArticleImage>();
		for(ArticleImageDto dto: dtos){
			domains.add(dtoToDomain(dto));
		}
		return domains;
	}

}
