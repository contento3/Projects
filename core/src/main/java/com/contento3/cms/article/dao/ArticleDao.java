package com.contento3.cms.article.dao;

import java.util.Collection;

import com.contento3.cms.article.model.Article;
import com.contento3.common.dao.GenericDao;

public interface ArticleDao extends GenericDao<Article,Integer> {


	/**
	 * Returns the collection of Article for a given accountId provided and by their Published/unpublished status
	 * @param accountId id for a site
	 * @param isPublished
	 * @return
	 */

	Collection<Article> findByAccountId(Integer accountId, boolean isPublished);
	
	/**
	 * Returns the Article for a given count and by their Published/unpublished status
	 * @param count 
	 * @param isPublished 
	 * @return 	  
	 */
	
	Collection<Article> findLatestArticle(Integer count, boolean isPublished);
	
	/**
	 * return article by their Published/unpublished status
	 * @param uuid
	 * @param isPublished 
	 * @return
	 */
	Article findByUuid(String uuid, boolean isPublished);
	
	/**
	 * find latest article by site id
	 * @param siteId
	 * @param isPublished 
	 * @return
	 */
	 Collection<Article> findLatestArticleBySiteId(Integer siteId,Integer count, Integer start, boolean isPublished);
	
	 /**
	  * find {@link Collection} of article for a given site and category
	 * @param numberOfArticles
	 * @param siteId
	 * @param isPublished 
	 * @param categoryName
	  * @return
	  */
	 Collection<Article> findLatestArticleByCategory(Collection<Integer> categoryIds,
			Integer numberOfArticles, Integer siteId, Integer start, boolean isPublished);
	 
	 /**
	  * Returns the article based on article, siteid by their Published/unpublished status
	  * @param articleId
	 * @param siteId
	 * @param isPublished 
	  * @return
	  */
	Article findArticleByIdAndSiteId(Integer articleId, Integer siteId, boolean isPublished);
	
	

		/**
		 * Returns article based on header name
		 * @param header

		 * @return
		 */
	//Collection<Article> findByHeaderName(String header);
	
	
	/**
	 * returns article based on header name, catagory assigned by their Published/unpublished status
	 * param header and catagory
	 *  @param isPublished 
	 */
	Collection<Article> findBySearch(String header, String catagory, boolean isPublished);
	
	
}


