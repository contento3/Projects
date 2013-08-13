package com.contento3.dam.image.library.dto;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.contento3.account.dto.AccountDto;
import com.contento3.common.dto.Dto;

public class ImageLibraryDto extends Dto {

	/**
	 * Primary key
	 */
	private Integer id;
	
	/**
	 * Name for image 
	 */
	private String name;
	
	/**
	 * Description of image
	 */
	private String description;
	
	/**
	 * Account associated to image library
	 */
	@ManyToOne
	@JoinColumn(name="ACCOUNT_ID")
	private AccountDto accountDto;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(final AccountDto accountDto) {
		this.accountDto = accountDto;
	}
}
