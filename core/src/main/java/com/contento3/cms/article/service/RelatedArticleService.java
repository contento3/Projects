package com.contento3.cms.article.service;

import java.util.Collection;

import com.contento3.cms.article.dto.RelatedArticleDto;
import com.contento3.cms.article.model.RelatedArticleLinkPK;

import com.contento3.common.service.Service;

public interface RelatedArticleService extends Service<RelatedArticleDto> {

	/**
	 * find  related articles associated to articleId
	 * @param articleId
	 * @return
	 */
	Collection<RelatedArticleDto> findRelatedArticles(Integer articleId);
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
