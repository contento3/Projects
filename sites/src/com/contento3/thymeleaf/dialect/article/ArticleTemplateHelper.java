package com.contento3.thymeleaf.dialect.article;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;

public class ArticleTemplateHelper {
    /**
     * Format a Joda DateTime using the given pattern.
     *
     * @param datetime
     * @param pattern
     * @return Formatted date string.
     */
	ArticleService articleService ;
   
	/**
	 * 
	 * @param accountId
	 * @param siteId
	 * @param catId
	 * @param count
	 * @param start
	 * @return
	 */
	public Collection <ArticleDto> getArticleListing(final Integer accountId, final Integer siteId, final Integer catId, final Integer count, Integer start) {
		if(start == null){
			start = 1;
		}
		start -= 1;
		if(start >0){
			start = start * count;
		}
		
		Collection<Integer> list = new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;
			{
			    add(catId);
			}
		};
		Collection<ArticleDto> articleList;
		if(catId != 0){
			articleList = this.articleService.findLatestArticleByCategory(list, siteId, count, start, true) ;
		}else{
			articleList = this.articleService.findLatestArticleBySiteId(siteId, count, start, true) ;
		}
       return articleList;
	}
	
	public ArticleDto getArticleById(final Integer accountId, final Integer siteId, final Integer articleId) {
		ArticleDto article;
		article = this.articleService.findById(articleId) ;
       return article ;
	}
	
	/**
	 * 
	 * @param accountId
	 * @param siteId
	 * @param catId
	 * @param count
	 * @return
	 */
	public Integer getPagesCount(final Integer accountId, final Integer siteId, Integer catId, Integer count){
		if(count == null || count == 0){
			count = 10;
		}
		if(catId == null)	catId =0;
		final Integer categoryId = catId;
		ArrayList<Integer> categoryIds = new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;
			{
			    add(categoryId);
			}
		};
		Collection<ArticleDto> articleList;
		if(categoryId != 0){
			articleList = this.articleService.findLatestArticleByCategory(categoryIds, siteId, null, 0, true) ;
		}else{
			articleList = this.articleService.findLatestArticleBySiteId(siteId, null, 0, true) ;
		}
		final Integer totalArticle = articleList.size();
		final Integer totalPages = totalArticle/count; 
		if(totalArticle%count == 0)
			return totalPages;
		else 
			return totalPages+1;
	}
	public ArticleTemplateHelper() {
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.articleService = (ArticleService) ctx.getBean("articleService");
	}
    
}