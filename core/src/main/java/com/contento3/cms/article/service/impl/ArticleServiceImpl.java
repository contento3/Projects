package com.contento3.cms.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.article.service.ArticleAssembler;
import com.contento3.cms.article.service.ArticleImageAssembler;
import com.contento3.cms.article.service.ArticleService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ArticleServiceImpl implements ArticleService {

	private ArticleAssembler articleAssembler;
	private ArticleDao articleDao;
	private ArticleImageAssembler articleImageAssembler;
	
	public ArticleServiceImpl(final ArticleAssembler articleAssembler,final ArticleDao articleDao,final ArticleImageAssembler articleImageAssembler) {
		this.articleAssembler = articleAssembler;
		this.articleDao = articleDao;
		this.articleImageAssembler = articleImageAssembler;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(ArticleDto articleDto){
		if(articleDto.getAssociateImagesDtos() == null)
			articleDto.setAssociateImagesDtos(new ArrayList<ArticleImageDto>());
		Article article = articleAssembler.dtoToDomain(articleDto);
		article.setAssociateImages(this.articleImageAssembler.dtosToDomains(articleDto.getAssociateImagesDtos()));
		return articleDao.persist(article);
	}
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(ArticleDto articleDto){
	
		articleDao.update(articleAssembler.dtoToDomain(articleDto));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findByAccountId(Integer accountId) {
		return articleAssembler.domainsToDtos(articleDao.findByAccountId(accountId));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findLatestArticle(int count) {
		
		return articleAssembler.domainsToDtos(articleDao.findLatestArticle(count));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ArticleDto findByUuid(String uuid) {
		
		return articleAssembler.domainToDto(articleDao.findByUuid(uuid));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ArticleDto findById(Integer id) {
		Article article = articleDao.findById(id);
		ArticleDto articleDto = articleAssembler.domainToDto(article);
		articleDto.setAssociateImagesDtos(this.articleImageAssembler.domainsToDtos(article.getAssociateImages()));
		return articleDto;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findLatestArticleBySiteId(Integer siteId,Integer count) {
		// TODO Auto-generated method stub
		return articleAssembler.domainsToDtos(articleDao.findLatestArticleBySiteId(siteId,count));
	}

	@Override
	public void delete(ArticleDto dtoToDelete) {
		// TODO Auto-generated method stub
		
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void updateAssociateImages(final ArticleDto articleDto){
		Article article = articleAssembler.dtoToDomain(articleDto);
		article.setAssociateImages(this.articleImageAssembler.dtosToDomains(articleDto.getAssociateImagesDtos()));
		this.articleDao.update(article);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findLatestArticleByCategory(
		final Integer categoryId, final Integer numberOfArticles, final Integer siteId) {
		return articleAssembler.domainsToDtos(articleDao.findLatestArticleByCategory(categoryId, numberOfArticles, siteId));
	}

	@Override
	public ArticleDto findArticleByIdAndSiteId(Integer id, Integer siteId) {
		Article article = articleDao.findArticleByIdAndSiteId(id, siteId);
		ArticleDto articleDto = null;
		if (article!=null){
			articleDto = articleAssembler.domainToDto(article);
		}
		return articleDto;
	}

}
