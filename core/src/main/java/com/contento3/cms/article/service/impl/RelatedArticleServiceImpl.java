package com.contento3.cms.article.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.article.dao.RelatedArticleDao;
import com.contento3.cms.article.dto.RelatedArticleDto;
import com.contento3.cms.article.service.RelatedArticleAssembler;
import com.contento3.cms.article.service.RelatedArticleService;
import com.contento3.common.exception.EntityAlreadyFoundException;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class RelatedArticleServiceImpl implements RelatedArticleService{
	
	private RelatedArticleAssembler  relatedArticleAssembler;
	private RelatedArticleDao relatedArticleDao;
	public RelatedArticleServiceImpl(final RelatedArticleAssembler relatedArticleAssembler,
			RelatedArticleDao relatedArticleDao) {
		Validate.notNull(relatedArticleAssembler,"relatedArticleAssembler cannot be null");
		Validate.notNull(relatedArticleDao,"relatedArticleDao cannot be null");
		// TODO Auto-generated constructor stub
		this.relatedArticleAssembler = relatedArticleAssembler;
		this.relatedArticleDao	= relatedArticleDao;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(RelatedArticleDto dto)
			throws EntityAlreadyFoundException {
		Validate.notNull(dto,"dto cannot be null");
		return null;		
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public 	Collection<RelatedArticleDto> findRelatedArticles(Integer articleId){
		Validate.notNull(articleId,"articleId cannot be null");
		return relatedArticleAssembler.domainsToDtos(relatedArticleDao.findRelatedArticles(articleId));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteRelatedArticle(Integer articleId) {
		Validate.notNull(articleId,"articleId cannot be null");
		relatedArticleDao.deleteRelatedArticle(articleId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void deleteRelatedArticles(Integer articleId,
			Collection<Integer> relatedArticleIds) {
		// TODO Auto-generated method stub
		Validate.notNull(articleId,"articleId cannot be null");
		Validate.notNull(relatedArticleIds,"relatedArticleIds cannot be null");
		relatedArticleDao.deleteRelatedArticles(articleId, relatedArticleIds);
		
	}

	@Override
	public void delete(RelatedArticleDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}

}
