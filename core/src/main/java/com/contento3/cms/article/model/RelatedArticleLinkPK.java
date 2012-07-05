package com.contento3.cms.article.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;



/**
 * this class us used for creating composite primary key 
 * (ARTICLE_ID,RELATED_ARTICLE_ID)
 *
 */
public class RelatedArticleLinkPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * RELATED_ARTICLE_ID used in composite pk generation 
	 */
	@ManyToOne
	@JoinColumn(name="RELATED_ARTICLE_ID")
	private Article relatedArticle;
	
	/**
	 * ARTICLE_ID used in composite pk generation 
	 */
	@ManyToOne
	@JoinColumn(name="ARTICLE_ID")
	private Article article;
	
	
	public Article getRelatedArticle() {
		return relatedArticle;
	}


	public void setRelatedArticle(Article relatedArticle) {
		this.relatedArticle = relatedArticle;
	}


	public Article getArticle() {
		return article;
	}


	public void setArticle(Article article) {
		this.article = article;
	}




	
	


	
}
