package com.contento3.thymeleaf.dialect.article;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.AuthorizationException;
import org.springframework.context.ApplicationContext;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.ContextLoader;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.article.service.ArticleService;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.cms.page.category.service.CategoryService;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.site.page.pathbuilder.PathBuilder;
import com.contento3.site.page.pathbuilder.context.ArticlePathBuilderContext;
import com.contento3.site.page.pathbuilder.impl.ArticlePathBuilder;

public class ArticleTemplateHelper {

	final ArticleService articleService ;

	final CategoryService categoryService;
	
	final PathBuilder<ArticlePathBuilderContext> articlePathBuilder;
	
	public ArticleTemplateHelper() {
		ApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
		this.articleService = (ArticleService) ctx.getBean("articleService");
		this.categoryService = (CategoryService) ctx.getBean("categoryService");
		this.articlePathBuilder = new ArticlePathBuilder();
	}
    

	/**
	 * 
	 * @param accountId
	 * @param siteId
	 * @param catId
	 * @param count
	 * @param start
	 * @return
	 * @throws EntityNotFoundException 
	 */
	public Collection <ArticleDto> getArticleListing(final Integer accountId, final Integer siteId, final Integer catId,final Boolean includeChildCategoryArticles, final Integer count, Integer start) throws EntityNotFoundException {
		if(start == null){
			start = 1;
		}
		start -= 1;
		if(start >0){
			start = start * count;
		}

		Collection<Integer> catIds=null; 
		if (includeChildCategoryArticles){
			catIds = fetchAllCategoryIds(catId,categoryService.findChildCategories(catId, accountId));
		}
		else {
			catIds = new ArrayList<Integer>();
			catIds.add(catId);
		}
		
		
		Collection<ArticleDto> articleList;
		if(catId != 0){
			articleList = this.articleService.findLatestArticleByCategory(catIds, siteId, count, start, true) ;
		}else{
			articleList = this.articleService.findLatestArticleBySiteId(siteId, count, start, true) ;
		}
		
		ArticlePathBuilderContext context = new ArticlePathBuilderContext(articleList,catIds,catId);
		articlePathBuilder.build(context);
		
		return articleList;
	}
	
	private Collection<Integer> fetchAllCategoryIds(final Integer catId,final Collection<CategoryDto> childCategories) {
		List <Integer> catIds = new ArrayList<Integer>();
		
		if (!CollectionUtils.isEmpty(childCategories)){
			for (CategoryDto category:childCategories){
				catIds.add(category.getCategoryId());
			}
		}
		
		//Add the parent category id too
		catIds.add(catId);
		
		return catIds;
	}


	public ArticleDto getArticleByQuery(final String query) {

		ArticleDto article=null;
		try {
			String articleInfo[] = query.split("/");
			
			//At the moment we are expecting to get the article's 
			//seo friendly url and uuid in the query string.
			
			//1. First try article by uuid 	 
			if (articleInfo.length==2){ //First try by uuid
				article = this.articleService.findByUuid(articleInfo[1],true) ;
			}
	
			if (articleInfo.length==2 && article==null){ //Then by id
				try {
				article = this.articleService.findById(Integer.parseInt(articleInfo[1]),true);
				}
				catch (final NumberFormatException nfe){
					//TODO add logging
				}
			}
		}
		catch (final AuthorizationException ae){
			
		}	
		
		return article;
	}

	public ArticleDto getArticleById(final Integer accountId, final Integer siteId, final Integer articleId) {
		ArticleDto article;
		article = this.articleService.findById(articleId);
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
	
}