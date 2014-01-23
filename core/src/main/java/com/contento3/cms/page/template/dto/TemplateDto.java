package com.contento3.cms.page.template.dto;

import com.contento3.account.dto.AccountDto;

public class TemplateDto {

	private Integer templateId;
	
	private TemplateTypeDto templateType;
	
	private boolean isGlobal;
	
	private String templateName;
	
	private String templateText;

	private TemplateDirectoryDto templateDirectoryDto;
	
	private AccountDto accountDto;
	
	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public TemplateTypeDto getTemplateType() {
		return templateType;
	}

	public void setTemplateType(TemplateTypeDto templateType) {
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

	public void setTemplateDirectoryDto(TemplateDirectoryDto templateDirectorDto) {
		this.templateDirectoryDto = templateDirectorDto;
	}

	public TemplateDirectoryDto getTemplateDirectoryDto() {
		return templateDirectoryDto;
	}

}
