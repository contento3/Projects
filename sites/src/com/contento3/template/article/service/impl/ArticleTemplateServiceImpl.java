package com.contento3.template.article.service.impl;

import java.util.List;

import org.apache.commons.lang.Validate;

import com.contento3.cms.article.service.ArticleService;
import com.contento3.template.article.dto.ArticleTemplateDto;
import com.contento3.template.article.service.ArticleTemplateService;

public class ArticleTemplateServiceImpl implements ArticleTemplateService {

	private ArticleTemplateAssembler articleTemplateAssembler;
	
	private ArticleService articleService;
	
	public ArticleTemplateServiceImpl (final ArticleTemplateAssembler articleTemplateAssembler,final ArticleService articleService){
		Validate.notNull(articleTemplateAssembler,"articleTemplateAssembler cannot be null");
		Validate.notNull(articleService,"articleService cannot be null");
		
		this.articleTemplateAssembler = articleTemplateAssembler;
		this.articleService = articleService;
	}
	
	@Override
	public List<ArticleTemplateDto> findLatestArticleByCategory(
			final Integer categoryId,final Integer numberOfArticles,final Integer siteId) {
		Validate.notNull(numberOfArticles,"numberOfArticles cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");
		return (List<ArticleTemplateDto>) articleTemplateAssembler.assemble(articleService.findLatestArticleByCategory(categoryId, numberOfArticles, siteId));
	}

	@Override
	public ArticleTemplateDto findArticleByCategoryName(String categoryName,
			Integer siteId) {
		Validate.notNull(categoryName,"categoryName cannot be null");
		Validate.notNull(siteId,"siteId cannot be null");
		return null;
	}

	public void setArticleTemplateAssembler(final ArticleTemplateAssembler articleTemplateAssembler){
		Validate.notNull(articleTemplateAssembler,"articleTemplateAssembler cannot be null");
		this.articleTemplateAssembler = articleTemplateAssembler;
	}

	public void setArticleService(final ArticleService articleService){
		Validate.notNull(articleService,"articleService cannot be null");
		this.articleService = articleService;
	}

	@Override
	public ArticleTemplateDto findArticleById(final Integer articleId) {
		Validate.notNull(articleId,"articleId cannot be null");
		return articleTemplateAssembler.assemble(articleService.findById(articleId));
	}

}
