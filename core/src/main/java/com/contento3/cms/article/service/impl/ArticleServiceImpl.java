package com.contento3.cms.article.service.impl;

import java.util.Collection;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.article.dao.ArticleDao;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.model.Article;
import com.contento3.cms.article.service.ArticleAssembler;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.site.structure.dto.SiteDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ArticleServiceImpl implements ArticleService {

	private ArticleAssembler articleAssembler;
	private ArticleDao articleDao;
	public ArticleServiceImpl(final ArticleAssembler articleAssembler,final ArticleDao articleDao) {
		this.articleAssembler = articleAssembler;
		this.articleDao = articleDao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void create(ArticleDto articleDto){
		articleDao.persist(articleAssembler.dtoToDomain(articleDto));
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

}
