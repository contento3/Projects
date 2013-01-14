package com.contento3.cms.article.service;

import java.util.Collection;

import com.contento3.cms.article.dto.ArticleImageDto;
import com.contento3.common.service.Service;

public interface ArticleImageService extends Service<ArticleImageDto>{

	/**
	 * Return collection of ArticleImage by articleId and ImageId
	 * @param articleId
	 * @param imageId
	 * @return
	 */
	Collection<ArticleImageDto> findAsscArticleImageById(final Integer articleId,final Integer imageId);
}
