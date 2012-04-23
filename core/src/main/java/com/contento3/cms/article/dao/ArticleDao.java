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
		
}
