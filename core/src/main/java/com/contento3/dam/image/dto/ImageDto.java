package com.contento3.dam.image.dto;

import java.util.Collection;

import com.contento3.account.dto.AccountDto;
import com.contento3.cms.site.structure.dto.SiteDto;

public class ImageDto {

	private String imageUuid;
	
	private Integer imageId;
	
	private String name;
	
	private String altText;
	
	private AccountDto accountDto;
	
	private byte[] image;
	
	
	/**
	 * sites that contains image
	 */
	private Collection<SiteDto> siteDto;

	public Collection<SiteDto> getSiteDto() {
		return siteDto;
	}

	public void setSiteDto(Collection<SiteDto> siteDto) {
		this.siteDto = siteDto;
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

	public Integer getImageId() {
		return imageId;
	}

	public void setImageId(Integer imageId) {
		this.imageId = imageId;
	}

	public String getImageUuid() {
		return imageUuid;
	}

	public void setImageUuid(String imageUuid) {
		this.imageUuid = imageUuid;
	}

}
