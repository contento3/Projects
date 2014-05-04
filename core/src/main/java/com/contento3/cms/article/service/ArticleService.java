package com.contento3.cms.article.service;

import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.common.service.StorableService;

public interface ArticleService extends StorableService<ArticleDto>{

	/**
	 * Returns the collection of Article for a given accountId provided
	 * @param accountId id for a site
	 * @param isPublished TODO
	 * @return
	 */

	Collection<ArticleDto> findByAccountId(Integer accountId, boolean isPublished);
	/**
	 * Returns the Article for a given count
	 * @param isPublished TODO
	 * @param count 
	 * @return 	  
	 */
	
	Collection<ArticleDto> findBySearch(String header, String catagory, boolean isPublished);
	/**
	 * used to search article by header name or catagory
	 * @param count
	 * @param isPublished TODO
	 * @return
	 */
	
	Collection<ArticleDto> findLatestArticle(int count, boolean isPublished);
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
	 * @param isPublished TODO
	 * @return
	 */
	ArticleDto findByUuid(String uuid, Boolean isPublished);
	
	/**
	 * find article by id
	 * @param id
	 * @return
	 */
	ArticleDto findById(Integer id);

	/**
	 * find article by id
	 * @param id
	 * @return
	 */
	ArticleDto findById(Integer id,Boolean isPublished);

	/**
	 * find latest article by site id
	 * @param siteId
	 * @param isPublished TODO
	 * @return
	 */
	Collection<ArticleDto> findLatestArticleBySiteId(Integer siteId, Integer limit, Integer start, boolean isPublished);
	
	/**
	 * Finds a {@link Collection} of Article for a site for a given category.
	 * @param siteId
	 * @param isPublished TODO
	 * @param categoryName
	 * @param numberOfArticles
	 * @return
	 */
	Collection<ArticleDto> findLatestArticleByCategory(Collection<Integer> categoryIds,Integer siteId, Integer limit, Integer start, boolean isPublished);

	/**
	 * Add associated images to article
	 * @param article
	 */
	void updateAssociateImages(ArticleDto article);
	
	/**
	 * 
	 * @param id
	 * @param siteId
	 * @param isPublished
	 * @return
	 */
	ArticleDto findArticleByIdAndSiteId(Integer id,Integer siteId, boolean isPublished);
}
