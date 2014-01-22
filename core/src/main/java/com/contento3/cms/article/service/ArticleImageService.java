package com.contento3.cms.article.service;

import java.util.Collection;

import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.service.StorableService;

public interface ArticleImageService extends StorableService<ArticleImageDto>{

	/**
	 * Return collection of ArticleImage by articleId and ImageId
	 * @param articleId
	 * @param imageId
	 * @return
	 */
	Collection<ArticleImageDto> findAsscArticleImageById(final Integer articleId,final Integer imageId);

	/**
	 * Return collection of ArticleImage by articleId and ImageId
	 * @param articleId
	 * @param imageId
	 * @return
	 */
	Collection<ArticleImageDto> findAsscArticleImageByArticleId(Integer articleId);

	/**
	 * 
	 * @param articleId
	 * @param scopeId
	 * @return
	 */
	Collection<ArticleImageDto> findAsscArticleImageByArticleIdAndScopeId(Integer articleId,Integer scopeId);
	
	/**
	 * 
	 * @param articleImageDtos
	 * @throws EntityCannotBeDeletedException
	 */
	void deleteAll(Collection <ArticleImageDto> articleImageDtos) throws EntityCannotBeDeletedException;

}
