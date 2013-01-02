package com.contento3.cms.article.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.contento3.account.model.Account;

@Entity
@Table( name = "ARTICLE_IMAGE_ASSOCIATION")
public class ArticleImage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Account for ArticleImage association
	 */
	@ManyToOne
	@JoinColumn(name = "ACCOUNT")
	private Account account;
	
		
	/**
	 * Primary key for ArticleImage
	 */
	@Id
	private ArticleImageLinkPK primaryKey;
	
	public final Account getAccount() {
		return account;
	}

	public final void setAccount(final Account account) {
		this.account = account;
	}

	public ArticleImageLinkPK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(final ArticleImageLinkPK primaryKey) {
		this.primaryKey = primaryKey;
	}

	

}
