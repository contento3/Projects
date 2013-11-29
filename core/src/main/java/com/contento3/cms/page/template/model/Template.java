package com.contento3.cms.page.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.contento3.account.model.Account;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "TEMPLATE")
public class Template {

	@Id  @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "TEMPLATE_ID")
	private Integer templateId;
	
	@OneToOne
	@JoinColumn (name="TEMPLATE_TYPE_ID")
	private TemplateType templateType;
	
	@Column(name = "IS_GLOBAL")
	private boolean isGlobal;
	
	@Column(name = "TEMPLATE_NAME")
	private String templateName;
	
	@Column(name = "TEMPLATE_TEXT")
	private String templateText;

	@OneToOne
	@JoinColumn(name = "TEMPLATE_DIRECTORY_ID")
	private TemplateDirectory directory;

	@OneToOne
	@JoinColumn(name = "ACCOUNT_ID")
	private Account account;

	@OneToOne
	@JoinColumn(name = "TEMPLATE_CATEGORY_ID")
	private TemplateCategory templateCategory;

	@Column(name = "TEMPLATE_PATH")
	private String templatePath;

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(final Integer templateId) {
		this.templateId = templateId;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public TemplateCategory getTemplateCategory() {
		return templateCategory;
	}

	public void setTemplateCategory(final TemplateCategory templateCategory) {
		this.templateCategory = templateCategory;
	}

	public void setTemplateType(final TemplateType templateType) {
		this.templateType = templateType;
	}

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(final boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(final String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateText() {
		return templateText;
	}

	public void setTemplateText(final String templateText) {
		this.templateText = templateText;
	}

	public void setDirectory(final TemplateDirectory directory) {
		this.directory = directory;
	}

	public TemplateDirectory getDirectory() {
		return directory;
	}

	public String getTemplatePath() {
		return templatePath;
	}

	public void setTemplatePath(final String templatePath) {
		this.templatePath = templatePath;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
}
