package com.contento3.template.article.service.impl;

import java.util.List;

import com.contento3.cms.article.service.ArticleService;
import com.contento3.template.article.dto.ArticleTemplateDto;
import com.contento3.template.article.service.ArticleTemplateService;

public class ArticleTemplateServiceImpl implements ArticleTemplateService {

	private ArticleTemplateAssembler articleTemplateAssembler;
	
	private ArticleService articleService;
	
	public ArticleTemplateServiceImpl (final ArticleTemplateAssembler articleTemplateAssembler,final ArticleService articleService){
		this.articleTemplateAssembler = articleTemplateAssembler;
		this.articleService = articleService;
	}
	
	@Override
	public List<ArticleTemplateDto> findLatestArticleByCategory(
			final Integer categoryId,final Integer numberOfArticles,final Integer siteId) {
		return (List<ArticleTemplateDto>) articleTemplateAssembler.assemble(articleService.findLatestArticleByCategory(categoryId, numberOfArticles, siteId));
	}

	@Override
	public ArticleTemplateDto findArticleByCategoryName(String categoryName,
			Integer siteId) {
		return null;
	}

	public void setArticleTemplateAssembler(final ArticleTemplateAssembler articleTemplateAssembler){
		this.articleTemplateAssembler = articleTemplateAssembler;
	}

	public void setArticleService(final ArticleService articleService){
		this.articleService = articleService;
	}

	@Override
	public ArticleTemplateDto findArticleById(final Integer articleId) {
		return articleTemplateAssembler.assemble(articleService.findById(articleId));
	}

}
