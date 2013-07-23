package com.contento3.template.article.service;

import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.template.article.dto.ArticleTemplateDto;

/**
 * Template Service for article.
 * @author HAMMAD
 *
 */
public interface ArticleTemplateService {

	/**
	 * Returns Collection of latest ArticlesDto for category for a site.
	 * We can also pass numberOfArticles to specify how many articles need 
	 * to be returened.If it is null then all the articles will be returned.   
	 * @param categoryName CategoryName to filter
	 * @param numberOfArticles Number of Articles to return
	 * @param siteId 
	 * @return
	 */
	Collection <ArticleTemplateDto> findLatestArticleByCategory(Integer categoryId,Integer numberOfArticles,Integer siteId);
	
	/**
	 * Finds the article by category name for a site.
	 * @param categoryName
	 * @param siteId
	 * @return
	 */
	ArticleTemplateDto findArticleByCategoryName(String categoryName,Integer siteId);
	
	/**
	 * Finds article by id 
	 * @param articleId
	 * @return
	 */
	ArticleTemplateDto findArticleById(Integer articleId);
	
}
