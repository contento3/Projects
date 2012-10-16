package com.contento3.web.content.article;

import org.vaadin.openesignforms.ckeditor.CKEditorConfig;
import org.vaadin.openesignforms.ckeditor.CKEditorTextField;

import com.contento3.web.common.UIContext;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class ArticleForm extends UIContext {
	/**
	 * Textfield for article heading
	 */
	private TextField articleHeading = new TextField();
	
	/**
	 * TextField article teaser
	 */
	private TextArea articleTeaser = new TextArea();
	
	/**
	 * CKEditor configuration.
	 */
	private CKEditorConfig config = new CKEditorConfig();
	
	/**
	 * CKEditor TextField used to add Article's body test.
	 */
	private CKEditorTextField bodyTextField = new CKEditorTextField(config);
	
	/**
	 * Article's posted date field.
	 */
	private PopupDateField postedDatefield = new PopupDateField(ArticleMgmtUIManager.ARTICLE_POSTED_DATE);
	
	/**
	 * Article's expiry date when the article gets expires
	 */
	private PopupDateField expiryDatefield = new PopupDateField(ArticleMgmtUIManager.ARTICLE_EXPIRY_DATE);

	/**
	 * Article Heading
	 * @return
	 */
	public TextField getArticleHeading() {
		return articleHeading;
	}

	/**
	 * Returns Article Teaser TextArea
	 * @return TextArea
	 */
	public TextArea getArticleTeaser() {
		return articleTeaser;
	}

	/**
	 * Configuration for the article's CKeditor
	 * @return
	 */
	public CKEditorConfig getConfig() {
		return config;
	}

	/**
	 * Returns CKEditorTextField 
	 * @return
	 */
	public CKEditorTextField getBodyTextField() {
		return bodyTextField;
	}

	/**
	 * Returns posted date field
	 * @return
	 */
	public PopupDateField getPostedDatefield() {
		return postedDatefield;
	}

	/**
	 * Returns expiry date
	 * @return
	 */
	public PopupDateField getExpiryDatefield() {
		return expiryDatefield;
	}

	/**
	 * Sets article heading TextField
	 * @param articleHeading
	 */
	public void setArticleHeading(final TextField articleHeading) {
		this.articleHeading = articleHeading;
	}

	/**
	 * Sets the article teaser TextArea
	 * @param articleTeaser
	 */
	public void setArticleTeaser(final TextArea articleTeaser) {
		this.articleTeaser = articleTeaser;
	}

	/**
	 * Set config
	 * @param config
	 */
	public void setConfig(final CKEditorConfig config) {
		this.config = config;
	}

	/**
	 * Sets bodytextfield
	 * @param bodyTextField
	 */
	public void setBodyTextField(final CKEditorTextField bodyTextField) {
		this.bodyTextField = bodyTextField;
	}

	/**
	 * Sets the PostedDateField
	 * @param postedDatefield
	 */
	public void setPostedDatefield(final PopupDateField postedDatefield) {
		this.postedDatefield = postedDatefield;
	}

	/**
	 * Sets Article expiry date
	 * @param expiryDatefield
	 */
	public void setExpiryDatefield(final PopupDateField expiryDatefield) {
		this.expiryDatefield = expiryDatefield;
	}

	
}
