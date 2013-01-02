package com.contento3.cms.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import com.contento3.account.service.AccountAssembler;
import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.model.ArticleImage;
import com.contento3.cms.article.service.ArticleImageAssembler;
import com.contento3.cms.content.dao.AssociatedContentScopeDao;
import com.contento3.dam.image.dao.ImageDao;

public class ArticleImageAssemblerImpl implements ArticleImageAssembler {

	private ArticleDao articleDao;
	private ImageDao imageDao;
	private AccountAssembler accountAssembler;
	private AssociatedContentScopeDao contentScopedao;

	/**
	 * Constructor
	 * @param articleDao
	 * @param imageDao
	 * @param contentScopeDao
	 * @param accountAssembler
	 */
	public ArticleImageAssemblerImpl(final ArticleDao articleDao,final ImageDao imageDao,final AssociatedContentScopeDao contentScopeDao,final AccountAssembler accountAssembler) {
		this.articleDao = articleDao;
		this.imageDao = imageDao;
		this.contentScopedao = contentScopeDao;
		this.accountAssembler = accountAssembler;
	}
	
	/**
	 * ArtilceImageDto to ArticleImage conversion
	 */
	@Override
	public ArticleImage dtoToDomain(final ArticleImageDto dto) {
		ArticleImage domain = new ArticleImage();
		domain.getPrimaryKey().setArticle(this.articleDao.findById(dto.getArticleId()));
		domain.getPrimaryKey().setImage(this.imageDao.findById(dto.getImageId()));
		domain.getPrimaryKey().setContentScope(this.contentScopedao.findById(dto.getContentScope()));
		domain.setAccount(this.accountAssembler.dtoToDomain(dto.getAccount()));
		return domain;
	}

	/**
	 * ArtilceImage to ArticleImageDto conversion
	 */
	@Override
	public ArticleImageDto domainToDto(final ArticleImage domain) {
		ArticleImageDto dto = new ArticleImageDto();
		dto.setArticleId(domain.getPrimaryKey().getArticle().getArticleId());
		dto.setImageId(domain.getPrimaryKey().getImage().getImageId());
		dto.setContentScope(domain.getPrimaryKey().getContentScope().getId());
		dto.setAccount(this.accountAssembler.domainToDto(domain.getAccount()));
		return dto;
	}

	/**
	 * Domains to Dtos Conversion
	 */
	@Override
	public Collection<ArticleImageDto> domainsToDtos(final Collection<ArticleImage> domains) {
		Collection<ArticleImageDto> dtos = new ArrayList<ArticleImageDto>();
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
		Collection<ArticleImage> domains = new ArrayList<ArticleImage>();
		for(ArticleImageDto dto: dtos){
			domains.add(dtoToDomain(dto));
		}
		return domains;
	}

}
