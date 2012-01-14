package com.contento3.cms.page.template.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.contento3.account.model.Account;

@Entity
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

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public TemplateType getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateType templateType) {
		this.templateType = templateType;
	}

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

	public String getTemplateName() {
		return templateName;
	}

	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateText() {
		return templateText;
	}

	public void setTemplateText(String templateText) {
		this.templateText = templateText;
	}

	public void setDirectory(TemplateDirectory directory) {
		this.directory = directory;
	}

	public TemplateDirectory getDirectory() {
		return directory;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Account getAccount() {
		return account;
	}
}
