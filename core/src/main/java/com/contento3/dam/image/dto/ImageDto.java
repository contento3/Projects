package com.contento3.dam.image.dto;

import com.contento3.account.dto.AccountDto;

public class ImageDto {

	private String imageUuid;
	
	private String name;
	
	private String altText;
	
	private AccountDto accountDto;
	
	private byte[] image;

	public String getImageId() {
		return imageUuid;
	}

	public void setImageId(String imageUuid) {
		this.imageUuid = imageUuid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAltText() {
		return altText;
	}

	public void setAltText(String altText) {
		this.altText = altText;
	}

	public AccountDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountDto accountDto) {
		this.accountDto = accountDto;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

}
