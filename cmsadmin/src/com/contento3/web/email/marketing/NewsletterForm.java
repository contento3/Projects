package com.contento3.web.email.marketing;

import com.contento3.web.common.Form;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class NewsletterForm implements Form {

	private TextField newsletterName;
	
	private TextArea description;
	
	private TextField templatePath;

	private Integer templateId;
	
	private TextField subject;
	
	public TextField getNewsletterName() {
		return newsletterName;
	}

	public void setNewsletterName(final TextField newsletterName) {
		this.newsletterName = newsletterName;
	}

	public TextArea getDescription() {
		return description;
	}

	public void setDescription(final TextArea description) {
		this.description = description;
	}

	public TextField getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(final TextField templatePath) {
		this.templatePath = templatePath;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public TextField getSubject() {
		return subject;
	}

	public void setSubject(TextField subject) {
		this.subject = subject;
	}
}
