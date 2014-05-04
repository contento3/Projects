package com.contento3.site.page.pathbuilder.context;

import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;

public class ArticlePathBuilderContext extends PathBuilderContext {
	private Collection<ArticleDto> articles;
	
	private Collection <Integer> categoryIds;

	private Integer mainCategoryId; 
	
	public ArticlePathBuilderContext (final Collection<ArticleDto> articles,final Collection <Integer> categoryIds,final Integer mainCategoryId){
		this.articles = articles;
		this.categoryIds = categoryIds;
		this.mainCategoryId = mainCategoryId;
	}
	
	public Collection<ArticleDto> getArticles() {
		return articles;
	}

	public void setArticles(final Collection<ArticleDto> articles) {
		this.articles = articles;
	}

	public Collection <Integer> getCategoryIds() {
		return categoryIds;
	}

	public void setCategoryIds(final Collection <Integer> categoryIds) {
		this.categoryIds = categoryIds;
	}

	public Integer getMainCategoryId() {
		return mainCategoryId;
	}

	public void setMainCategoryId(Integer mainCategoryId) {
		this.mainCategoryId = mainCategoryId;
	}
	
	
}
