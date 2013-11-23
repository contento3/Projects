package com.contento3.cms.article.service.impl;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
		Validate.notNull(articleAssembler,"articleAssembler cannot be null");
		Validate.notNull(articleDao,"articleDao cannot be null");
		Validate.notNull(articleImageAssembler,"articleImageAssembler cannot be null");
		
		this.articleAssembler = articleAssembler;
		this.articleDao = articleDao;
		this.articleImageAssembler = articleImageAssembler;
	}
	@RequiresPermissions("article:add")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(ArticleDto articleDto){
	
		Validate.notNull(articleDto,"articleDto cannot be null");
			if(articleDto.getAssociateImagesDtos() == null)
				articleDto.setAssociateImagesDtos(new ArrayList<ArticleImageDto>());
		
		Article article = articleAssembler.dtoToDomain(articleDto);
		article.setAssociateImages(this.articleImageAssembler.dtosToDomains(articleDto.getAssociateImagesDtos()));
		return articleDao.persist(article);
		}
		
		
		
	
	@Transactional(readOnly = false,propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(ArticleDto articleDto){
		Validate.notNull(articleDto,"articleDto cannot be null");
		articleDao.update(articleAssembler.dtoToDomain(articleDto));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findByAccountId(Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		return articleAssembler.domainsToDtos(articleDao.findByAccountId(accountId));
	}
	

	@Override
	public Collection<ArticleDto> findBySearch(String header, String catagory) {
		Validate.notNull(header,"header cannot be null");
		Validate.notNull(catagory,"catagory cannot be null");
		
		return articleAssembler.domainsToDtos(articleDao.findBySearch(header,catagory));
	}
	
	

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findLatestArticle(int count) {
		Validate.notNull(count,"count cannot be null");
		
		return articleAssembler.domainsToDtos(articleDao.findLatestArticle(count));
	}

		
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ArticleDto findByUuid(String uuid) {
		Validate.notNull(uuid,"uuid cannot be null");
		return articleAssembler.domainToDto(articleDao.findByUuid(uuid));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ArticleDto findById(Integer id) {
		Validate.notNull(id,"id cannot be null");
		Article article = articleDao.findById(id);
		ArticleDto articleDto = articleAssembler.domainToDto(article);
		articleDto.setAssociateImagesDtos(this.articleImageAssembler.domainsToDtos(article.getAssociateImages()));
		return articleDto;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findLatestArticleBySiteId(Integer siteId,Integer count) {
		// TODO Auto-generated method stub
		Validate.notNull(siteId,"siteId cannot be null");
		//Validate.notNull(count,"count cannot be null");
		return articleAssembler.domainsToDtos(articleDao.findLatestArticleBySiteId(siteId,count));
	}

	@Override
	public void delete(ArticleDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void updateAssociateImages(final ArticleDto articleDto){
		Validate.notNull(articleDto,"articleDto cannot be null");
		Article article = articleAssembler.dtoToDomain(articleDto);
		article.setAssociateImages(this.articleImageAssembler.dtosToDomains(articleDto.getAssociateImagesDtos()));
		this.articleDao.update(article);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleDto> findLatestArticleByCategory(
		final Collection<Integer> categoryIds, final Integer numberOfArticles, final Integer siteId) {
		Validate.notNull(categoryIds,"categoryIds collection cannot be null");
		Validate.notNull(numberOfArticles,"numberOfArticles cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");
		
		return articleAssembler.domainsToDtos(articleDao.findLatestArticleByCategory(categoryIds, numberOfArticles, siteId));
	}

	@Override
	public ArticleDto findArticleByIdAndSiteId(Integer id, Integer siteId) {
		Validate.notNull(id,"id cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");
		
		Article article = articleDao.findArticleByIdAndSiteId(id, siteId);
		ArticleDto articleDto = null;
		if (article!=null){
			articleDto = articleAssembler.domainToDto(article);
		}
		return articleDto;
	}
	
	
}
