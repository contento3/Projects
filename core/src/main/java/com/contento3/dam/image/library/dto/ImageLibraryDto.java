package com.contento3.dam.image.library.dto;

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
}
