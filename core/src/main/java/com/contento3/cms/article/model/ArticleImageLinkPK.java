package com.contento3.cms.article.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.contento3.cms.content.model.AssociatedContentScope;
import com.contento3.dam.image.model.Image;
/**
 * ArticleImageLinkPK class used to create composite key
 */
@Embeddable
public class ArticleImageLinkPK implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Article_Id is used for composite key generation
	 */
	@ManyToOne
	@JoinColumn(name ="ARTICLE")
	private Article article;

	/**
	 * Image_Id is used for composite key generation
	 */
	@ManyToOne
	@JoinColumn(name ="IMAGE")
	private Image image;
	
	/**
	 * Content scope is used for composite key generation
	 */
	@ManyToOne
	@JoinColumn( name = "Associated_Content_Scope")
	private AssociatedContentScope contentScope;

	public final Article getArticle() {
		return article;
	}

	public final void setArticle(final Article article) {
		this.article = article;
	}

	public final Image getImage() {
		return image;
	}

	public final void setImage(final Image image) {
		this.image = image;
	}

	public final AssociatedContentScope getContentScope() {
		return contentScope;
	}

	public final void setContentScope(final AssociatedContentScope contentScope) {
		this.contentScope = contentScope;
	}

	
}
