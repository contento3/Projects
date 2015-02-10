package com.contento3.cms.article.service.impl;

import java.util.Collection;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.cms.article.dao.ArticleImageDao;
import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.cms.article.model.ArticleImage;
import com.contento3.cms.article.service.ArticleImageAssembler;
import com.contento3.cms.article.service.ArticleImageService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;

public class ArticleImageServiceImpl implements ArticleImageService {

	private ArticleImageAssembler articleImageAssembler;
	
	private ArticleImageDao articleImageDao;
	
	public ArticleImageServiceImpl(final ArticleImageAssembler articleImageAssembler,final ArticleImageDao articleImageDao) {
		Validate.notNull(articleImageAssembler,"articleImageAssembler cannot be null");
		Validate.notNull(articleImageDao,"articleImageDao cannot be null");
		
		this.articleImageAssembler = articleImageAssembler;
		this.articleImageDao = articleImageDao;
	}
	@RequiresPermissions("ARTICLE_IMAGE_ASSOCIATION:ADD")
	@Override
	public Object create(final ArticleImageDto dto)
			throws EntityAlreadyFoundException {
		Validate.notNull(dto,"dto cannot be null");
		return null;
	}
	@RequiresPermissions("ARTICLE_IMAGE_ASSOCIATION:DELETE")
	@Override
	public void delete(final ArticleImageDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		Validate.notNull(dtoToDelete,"dtoToDelete to delete is not null");
		this.articleImageDao.delete(articleImageAssembler.dtoToDomain(dtoToDelete));
	}
	
	@RequiresPermissions("ARTICLE_IMAGE_ASSOCIATION:VIEW")
	@Transactional(readOnly=true,propagation=Propagation.REQUIRES_NEW)
	@Override
	public Collection<ArticleImageDto> findAsscArticleImageById(final Integer articleId,final Integer imageId) {
		Validate.notNull(articleId,"articleId cannot null");
		Validate.notNull(imageId,"imageId cannot null");
		return this.articleImageAssembler.domainsToDtos(this.articleImageDao.findAsscArticleImageById(articleId, imageId));
	}
	
	@RequiresPermissions("ARTICLE_IMAGE_ASSOCIATION:VIEW")
	@Override
	public Collection<ArticleImageDto> findAsscArticleImageByArticleId(
			final Integer articleId) {
		Validate.notNull(articleId,"articleId cannot null");
		return this.articleImageAssembler.domainsToDtos(this.articleImageDao.findAsscArticleImageByArticleId(articleId));
	}
	//@RequiresPermissions("ARTICLE_IMAGE_ASSOCIATION:DELETE")
	@Override
	public void deleteAll(final Collection<ArticleImageDto> articleImageDtos) throws EntityCannotBeDeletedException {
		Validate.notNull(articleImageDtos,"articleImageDtos cannot null");
		Iterator<ArticleImageDto> it = articleImageDtos.iterator();
		while (it.hasNext()){
			delete ((ArticleImageDto)it.next());
		}
	}
	
	//@RequiresPermissions("ARTICLE_IMAGE_ASSOCIATION:VIEW")
	@Override
	public Collection<ArticleImageDto> findAsscArticleImageByArticleIdAndScopeId(final Integer articleId,final Integer scopeId) {
		Validate.notNull(articleId,"articleId cannot null");
		Validate.notNull(scopeId,"scopeId cannot null");
		return this.articleImageAssembler.domainsToDtos(this.articleImageDao.findAsscArticleImageByArticleIdAndScopeId(articleId,scopeId));
	}
	
	@Override
	public Collection<ArticleImageDto> findArticleImageByImageId(Integer imageId) {
		Validate.notNull(imageId,"imageId cannot null");
		return this.articleImageAssembler.domainsToDtos(this.articleImageDao.findArticleImageByImageId(imageId));
	}

}
