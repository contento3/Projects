package com.contento3.cms.article.dto;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.content.dto.AssociatedContentScopeDto;
import com.contento3.common.dto.Dto;
import com.contento3.dam.image.dto.ImageDto;

public class ArticleImageDto extends Dto {
	/**
	 * Id for article
	 */
	private ArticleDto article;

	/**
	 * Id for images 
	 */
	private ImageDto image;
	
	/**
	 * Id for content scope
	 */
	private AssociatedContentScopeDto contentScope;
	
	/**
	 * account on which articles and images are associated
	 */
	private AccountDto account;
	
	@Override
	public boolean equals(Object v) {
		boolean retVal = false;

		if (v instanceof ArticleImageDto) {
			ArticleImageDto ptr = (ArticleImageDto) v;
			retVal = (ptr.article.getArticleId() == this.article.getArticleId()
					&& ptr.image.getImageId() == this.image.getImageId() && ptr.contentScope
					.getId() == this.contentScope.getId());
		}

		return retVal;
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 17
				* hash
				+ (this.image.getId() != null ? this.image.getId().hashCode()
						: 0);
		return hash;
	}
	
	public final ArticleDto getArticle() {
		return article;
	}

	public final void setArticle(final ArticleDto article) {
		this.article = article;
	}

	public final ImageDto getImage() {
		return image;
	}

	public final void setImage(final ImageDto image) {
		this.image = image;
	}

	public final AssociatedContentScopeDto getContentScope() {
		return contentScope;
	}

	public final void setContentScope(final AssociatedContentScopeDto contentScope) {
		this.contentScope = contentScope;
	}

	public final AccountDto getAccount() {
		return account;
	}

	public final void setAccount(final AccountDto account) {
		this.account = account;
	}
		
}
