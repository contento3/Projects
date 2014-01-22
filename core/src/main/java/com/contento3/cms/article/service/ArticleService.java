package com.contento3.cms.article.service;

import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.common.service.StorableService;

public interface ArticleService extends StorableService<ArticleDto>{

	/**
	 * Returns the collection of Article for a given accountId provided
	 * @param accountId id for a site
	 * @return
	 */

	Collection<ArticleDto> findByAccountId(Integer accountId);
	/**
	 * Returns the Article for a given count
	 * @param count 
	 * @return 	  
	 */
	
	Collection<ArticleDto> findBySearch(String header, String catagory);
	/**
	 * used to search article by header name or catagory
	 * @param count
	 * @return
	 */
	
	Collection<ArticleDto> findLatestArticle(int count);
	/**
	 * used to create new article
	 * @param articleDto
	 */
	Integer create(ArticleDto articleDto);
	/**
	 * used to update article
	 * @param articleDto
	 */
	void update(ArticleDto articleDto);
	
	/**
	 * return article
	 * @param uuid
	 * @return
	 */
	ArticleDto findByUuid(String uuid);
	
	/**
	 * find article by id
	 * @param id
	 * @return
	 */
	ArticleDto findById(Integer id);
	
	/**
	 * find latest article by site id
	 * @param siteId
	 * @return
	 */
	Collection<ArticleDto> findLatestArticleBySiteId(Integer siteId,Integer count);
	
	/**
	 * Finds a {@link Collection} of Article for a site for a given category.
	 * @param categoryName
	 * @param numberOfArticles
	 * @param siteId
	 * @return
	 */
	Collection<ArticleDto> findLatestArticleByCategory(Collection<Integer> categoryIds,Integer numberOfArticles,Integer siteId);

	/**
	 * Add associated images to article
	 * @param article
	 */
	void updateAssociateImages(ArticleDto article);
	
	ArticleDto findArticleByIdAndSiteId(Integer id,Integer siteId);

	
	
}
