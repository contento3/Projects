package com.contento3.thymeleaf.dialect.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.thymeleaf.context.IProcessingContext;
import org.thymeleaf.dialect.AbstractDialect;
import org.thymeleaf.dialect.IExpressionEnhancingDialect;
import org.thymeleaf.processor.IProcessor;

import com.amazonaws.http.HttpRequest;
import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.page.category.dao.impl.CategoryDaoHibernateImpl;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.cms.page.category.service.impl.CategoryServiceImpl;
import com.contento3.cms.page.dao.impl.PageDaoHibernateImplTest;
import com.contento3.site.template.model.TemplateModelMap;
import com.contento3.site.template.model.TemplateModelMapImpl;

public class ArticleUtility {
    /**
     * Format a Joda DateTime using the given pattern.
     *
     * @param datetime
     * @param pattern
     * @return Formatted date string.
     */
	ArticleService articleService ;
   
	public ArrayList<ArticleDto> getArticleListing(final Integer accountId, final Integer siteId, final Integer catId, final Integer count, Integer start) {
		if(start == null){
			start = 1;
		}
		start -= 1;
		if(start >0){
			start = start * count;
		}
		ArrayList<Integer> list = new ArrayList<Integer>() {
			private static final long serialVersionUID = 1L;
			{
			    add(catId);
			}
		};
		Collection<ArticleDto> articleList;
		if(catId != 0){
			articleList = this.articleService.findLatestArticleByCategory(list, siteId, count, start) ;
		}else{
			articleList = this.articleService.findLatestArticleBySiteId(siteId, count, start) ;
		}
       return (ArrayList<ArticleDto>) articleList;
	}
	
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
			articleList = this.articleService.findLatestArticleByCategory(categoryIds, siteId, null, 0) ;
		}else{
			articleList = this.articleService.findLatestArticleBySiteId(siteId, null, 0) ;
		}
		final Integer totalArticle = articleList.size();
		final Integer totalPages = totalArticle/count; 
		if(totalArticle%count == 0)
			return totalPages;
		else 
			return totalPages+1;
	}
	public ArticleUtility() {
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.articleService = (ArticleService) ctx.getBean("articleService");
	}
    
}