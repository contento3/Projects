package com.contento3.cms.article.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.article.model.ArticleImage;
import com.contento3.cms.article.service.ArticleAssembler;
import com.contento3.cms.article.service.ArticleImageAssembler;
import com.contento3.cms.article.service.ArticleService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ArticleServiceImpl implements ArticleService {

	private ArticleAssembler articleAssembler;
	private ArticleDao articleDao;
	private ArticleImageAssembler articleImageAssembler;
	
	public ArticleServiceImpl(final ArticleAssembler articleAssembler,final ArticleDao articleDao, final ArticleImageAssembler articleImageAssembler) {
		this.articleAssembler = articleAssembler;
		this.articleDao = articleDao;
		this.articleImageAssembler = articleImageAssembler;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(ArticleDto articleDto){
		return articleDao.persist(articleAssembler.dtoToDomain(articleDto));
	}
	
	@Transactional(readOnly = false)
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
		
		return articleAssembler.domainToDto(articleDao.findById(id));
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
	
	@Override
	public void associateImages(final Integer articleId,final Collection<ArticleImageDto> dto){
	
		Collection<ArticleImage> associatedImages = this.articleImageAssembler.dtosToDomains(dto);
		Article article = this.articleDao.findById(articleId);
		article.setAssociateImages(associatedImages);
		articleDao.update(article);
	}

}
