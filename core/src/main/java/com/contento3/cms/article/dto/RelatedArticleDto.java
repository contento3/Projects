package com.contento3.cms.article.dto;

import com.contento3.cms.article.model.RelatedArticleLinkPK;

public class RelatedArticleDto {
	
	/**
	 * Composite primary key 
	 */
	private RelatedArticleLinkPK primaryKey;
	/**
	 * contains articleId which are related
	 */
	private ArticleDto article;
	/**
	 * relation ship type
	 */
	private String type;
	
	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}
	
	public ArticleDto getArticle() {
		return article;
	}

	public void setArticle(final ArticleDto article) {
		this.article = article;
	}

	public RelatedArticleLinkPK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(final RelatedArticleLinkPK primaryKey) {
		this.primaryKey = primaryKey;
	}

}
