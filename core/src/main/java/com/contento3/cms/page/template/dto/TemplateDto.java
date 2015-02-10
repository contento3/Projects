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
	
	private String templatePath;
	
	private String templateKey;
	
	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(final AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(final Integer templateId) {
		this.templateId = templateId;
	}

	public TemplateTypeDto getTemplateType() {
		return templateType;
	}

	public void setTemplateType(final TemplateTypeDto templateType) {
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

	public void setTemplateName(final String templateName) {
		this.templateName = templateName;
	}

	public String getTemplateText() {
		return templateText;
	}

	public void setTemplateText(final String templateText) {
		this.templateText = templateText;
	}

	public void setTemplateDirectoryDto(final TemplateDirectoryDto templateDirectorDto) {
		this.templateDirectoryDto = templateDirectorDto;
	}

	public TemplateDirectoryDto getTemplateDirectoryDto() {
		return templateDirectoryDto;
	}

    public String getTemplatePath() {
        return templatePath;
}

    public void setTemplatePath(final String templatePath) {
        this.templatePath = templatePath;
    }

	public String getTemplateKey() {
		return templateKey;
	}

	public void setTemplateKey(final String templateKey) {
		this.templateKey = templateKey;
	}

}
