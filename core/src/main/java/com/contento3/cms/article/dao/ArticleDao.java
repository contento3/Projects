package com.contento3.cms.article.dao;

import java.util.Collection;

import com.contento3.cms.article.model.Article;
import com.contento3.common.dao.GenericDao;

public interface ArticleDao extends GenericDao<Article,Integer> {


	/**
	 * Returns the collection of Article for a given accountId provided
	 * @param accountId id for a site
	 * @return
	 */

	Collection<Article> findByAccountId(Integer accountId);
	/**
	 * Returns the Article for a given count
	 * @param count 
	 * @return 	  
	 */

	Collection<Article> findLatestArticle(int count);
	
	
	/**
	 * return article
	 * @param uuid
	 * @return
	 */
	Article findByUuid(String uuid);
	/**
	 * find latest article by site id
	 * @param siteId
	 * @return
	 */
	 Collection<Article> findLatestArticleBySiteId(Integer siteId,Integer count);
	
	 /**
	  * find {@link Collection} of article for a given site and category
	  * @param categoryName
	  * @param numberOfArticles
	  * @param siteId
	  * @return
	  */
	 Collection<Article> findLatestArticleByCategory(Integer categoryId,
			Integer numberOfArticles, Integer siteId);
	 
	 /**
	  * Returns the article based on article and siteid
	  * @param articleId
	  * @param siteId
	  * @return
	  */
	Article findArticleByIdAndSiteId(Integer articleId, Integer siteId);
		
}
