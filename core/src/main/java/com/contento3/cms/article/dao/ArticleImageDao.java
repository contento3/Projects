package com.contento3.cms.article.dao;

import java.util.Collection;

import com.contento3.cms.article.model.ArticleImage;
import com.contento3.common.dao.GenericDao;


public interface ArticleImageDao extends GenericDao<ArticleImage, Integer> {
	
	/**
	 * Return collection of ArticleImage by imageId and articleId
	 * @param articleId
	 * @param imageId
	 * @return Collection<ArticleImage>
	 */
	Collection<ArticleImage> findAsscArticleImageById(Integer articleId,Integer imageId);

	/**
	 * Return collection of ArticleImage by articleId
	 * @param articleId
	 * @return Collection<ArticleImage>
	 */
	Collection<ArticleImage> findAsscArticleImageByArticleId(Integer articleId);

	Collection<ArticleImage> findAsscArticleImageByArticleIdAndScopeId(
			Integer articleId, Integer scopeId);

}
