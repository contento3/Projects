package com.contento3.site.page.pathbuilder.impl;

import java.util.Collection;

import org.apache.shiro.util.CollectionUtils;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.cms.page.category.dto.CategoryDto;
import com.contento3.site.page.pathbuilder.PathBuilder;
import com.contento3.site.page.pathbuilder.context.ArticlePathBuilderContext;

public class ArticlePathBuilder implements PathBuilder<ArticlePathBuilderContext> {
	
	@Override
	public void build(final ArticlePathBuilderContext context) {
		final Collection <ArticleDto> articles = context.getArticles();
		final Collection <Integer> categoryIds = context.getCategoryIds();
		final Integer mainCategoryId = context.getMainCategoryId();
		
		for (ArticleDto article : articles){
			final Collection<CategoryDto> articleCategories = article.getCategoryDtos();
			if(!CollectionUtils.isEmpty(articleCategories)){
				String articlePath = findCategoryPath(categoryIds,articleCategories,mainCategoryId);
				articlePath = articlePath+"article/"+article.getSeoFriendlyUrl()+"/"+article.getUuid();
				article.setArticlePath(articlePath.toLowerCase());
			}
		}
	}

	private String findCategoryPath(final Collection <Integer> categoryIds,final Collection<CategoryDto> articleCategories,final Integer mainCategoryId){
		for (CategoryDto category:articleCategories){
			
			String path = "";
			//Get Main categoryId
			if (mainCategoryId==category.getId() || categoryIds.contains(category.getId())){
				return getChildCategory(path,category);
			}
		}
		return "";
	}
	
	private String getChildCategory(String path,final CategoryDto category){
		if (category!=null){
			path = category.getName()!=null?category.getName().replace(" ", "-")+ "/" + path:"";
			return getChildCategory(path,category.getParent());
		}
		return path;
	}
	
}
