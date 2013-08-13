package com.contento3.cms.article.service;

import java.util.Collection;

import com.contento3.cms.article.dto.RelatedArticleDto;
import com.contento3.common.service.Service;

public interface RelatedArticleService extends Service<RelatedArticleDto> {

	/**
	 * find  related articles associated to articleId
	 * @param articleId
	 * @return
	 */
	Collection<RelatedArticleDto> findRelatedArticles(final Integer articleId);
	/**
	 * delete related article
	 * @param articleId
	 */
	void deleteRelatedArticle(final Integer articleId);
	/**
	 * delete collection of related articles
	 * @param articleId
	 * @param relatedArticleIds
	 */
	void deleteRelatedArticles(final Integer articleId,final  Collection<Integer> relatedArticleIds);
}
