package com.contento3.cms.article.dao;


import java.util.Collection;

import com.contento3.cms.article.model.RelatedArticle;
import com.contento3.common.dao.GenericDao;

public interface RelatedArticleDao extends GenericDao<RelatedArticle,Integer>{
	/**
	 * find  related articles associated to articleId
	 * @param articleId
	 * @return
	 */
	Collection<RelatedArticle> findRelatedArticles(Integer articleId);
	/**
	 * delete related article
	 * @param articleId
	 */
	void deleteRelatedArticle(Integer articleId);
	/**
	 * delete collection of related articles
	 * @param articleId
	 * @param relatedArticleIds
	 */
	void deleteRelatedArticles(Integer articleId, Collection<Integer> relatedArticleIds);
}
