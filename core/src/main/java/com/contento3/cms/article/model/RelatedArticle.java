package com.contento3.cms.article.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="Related_Articles")
public class RelatedArticle implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * relation ship type
	 */
	@Column(name ="TYPE")
	private String type;
	/**
	 * Composite primary key 
	 */
	@EmbeddedId
	private RelatedArticleLinkPK primaryKey;
	
	/**
	 * contains articleId which are related
	 */
	@ManyToOne
	@JoinColumn(name="ARTICLE_ID", insertable=false, updatable=false)
	private Article article;
	  
	  
	
	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public RelatedArticleLinkPK getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(RelatedArticleLinkPK primaryKey) {
		this.primaryKey = primaryKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
