package com.contento3.template.article.service.impl;

import com.contento3.template.image.dto.ImageTemplateDto;

public class ArticleImageTemplateDto {

	/**
	 * Id for article
	 */
	private Integer articleId;

	/**
	 * Id for images 
	 */
	private ImageTemplateDto image;
	
	/**
	 * Id for content scope
	 */
	private String contentScope;

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(final Integer articleId) {
		this.articleId = articleId;
	}

	public ImageTemplateDto getImage() {
		return image;
	}

	public void setImage(final ImageTemplateDto image) {
		this.image = image;
	}

	public String getContentScope() {
		return contentScope;
	}

	public void setContentScope(final String contentScope) {
		this.contentScope = contentScope;
	}

	
}
