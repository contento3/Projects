package com.contento3.cms.article.service.impl;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.article.dao.ArticleImageDao;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.service.ArticleImageAssembler;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;

public class ArticleImageServiceImpl implements ArticleImageService {

	private ArticleImageAssembler articleImageAssembler;
	private ArticleImageDao articleImageDao;
	public ArticleImageServiceImpl(final ArticleImageAssembler articleImageAssembler,final ArticleImageDao articleImageDao) {
		this.articleImageAssembler = articleImageAssembler;
		this.articleImageDao = articleImageDao;
	}
	
	@Override
	public Object create(final ArticleImageDto dto)
			throws EntityAlreadyFoundException {
	
		return null;
	}

	@Override
	public void delete(final ArticleImageDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		this.articleImageDao.delete(articleImageAssembler.dtoToDomain(dtoToDelete));
	}

	@Transactional(readOnly=true,propagation=Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleImageDto> findAsscArticleImageById(Integer articleId, Integer imageId) {
		return this.articleImageAssembler.domainsToDtos(this.articleImageDao.findAsscArticleImageById(articleId, imageId));
	}

	@Override
	public Collection<ArticleImageDto> findAsscArticleImageByArticleId(
			Integer articleId) {
		return this.articleImageAssembler.domainsToDtos(this.articleImageDao.findAsscArticleImageByArticleId(articleId));
	}

	@Override
	public void deleteAll(Collection<ArticleImageDto> articleImageDtos) throws EntityCannotBeDeletedException {
		Iterator<ArticleImageDto> it = articleImageDtos.iterator();
		while (it.hasNext()){
			delete ((ArticleImageDto)it.next());
		}
	}

	@Override
	public Collection<ArticleImageDto> findAsscArticleImageByArticleIdAndScopeId(
			Integer articleId, Integer scopeId) {
		return this.articleImageAssembler.domainsToDtos(this.articleImageDao.findAsscArticleImageByArticleIdAndScopeId(articleId,scopeId));
	}

}
