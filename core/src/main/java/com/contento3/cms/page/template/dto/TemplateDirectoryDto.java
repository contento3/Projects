package com.contento3.cms.page.template.dto;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.account.dto.AccountDto;

/**
 * DTO for template directory
 * @author HAMMAD
 *
 */
public class TemplateDirectoryDto {

	private Integer id;
	
	private String directoryName;
	
	private TemplateDirectoryDto parent;
	
	private Collection<TemplateDirectoryDto> childDirectories = new ArrayList<TemplateDirectoryDto>();

	private boolean isGlobal;
	
	private AccountDto account;


	public AccountDto getAccount() {
		return account;
	}

	public void setAccount(final AccountDto account) {
		this.account = account;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(final String directoryName) {
		this.directoryName = directoryName;
	}

	public TemplateDirectoryDto getParent() {
		return parent;
	}

	public void setParent(final TemplateDirectoryDto parent) {
		this.parent = parent;
	}

	public Collection<TemplateDirectoryDto> getChildDirectories() {
		return childDirectories;
	}

	public void setChildDirectories(final Collection<TemplateDirectoryDto> childDirectories) {
		this.childDirectories = childDirectories;
	}

	public boolean isGlobal() {
		return isGlobal;
	}

	public void setGlobal(final boolean isGlobal) {
		this.isGlobal = isGlobal;
	}

}
