package com.contento3.cms.article.dao;

import java.util.Collection;

import com.contento3.cms.article.model.ArticleImage;
import com.contento3.common.dao.GenericDao;


public interface ArticleImageDao extends GenericDao<ArticleImage, Integer> {
	
	
	/**
	 * Return collection of ArticleImage by imageid and article id
	 * @param articleId
	 * @param imageId
	 * @return
	 */
	Collection<ArticleImage> findAsscArticleImageById(final Integer articleId,final Integer imageId);

}
