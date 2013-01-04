package com.contento3.cms.article.dto;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;

public class ArticleImageDto extends Dto {
	/**
	 * Id for article
	 */
	private Integer articleId;

	/**
	 * Id for images 
	 */
	private Integer imageId;
	
	/**
	 * Id for content scope
	 */
	private Integer contentScope;
	
	/**
	 * account on which articles and images are associated
	 */
	private AccountDto account;

	public final Integer getArticleId() {
		return articleId;
	}

	public final void setArticleId(final Integer articleId) {
		this.articleId = articleId;
	}

	public final Integer getImageId() {
		return imageId;
	}

	public final void setImageId(final Integer imageId) {
		this.imageId = imageId;
	}

	public final AccountDto getAccount() {
		return account;
	}

	public final void setAccount(final AccountDto account) {
		this.account = account;
	}

	public final Integer getContentScope() {
		return contentScope;
	}

	public final void setContentScope(final Integer contentScope) {
		this.contentScope = contentScope;
	}

	
}
