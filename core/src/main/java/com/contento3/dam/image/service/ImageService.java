package com.contento3.dam.image.service;

import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.dam.image.dto.ImageDto;

public interface ImageService extends Service<ImageDto> {

	/**
	 * Finds image by uuid
	 * @param imageId
	 * @return ImageDto
	 */
	ImageDto findImageById(String imageId);

	/**
	 * Returns {@link java.util.Collection}} an image by accountId
	 * @param accountId
	 * @return ImageDto
	 */
	Collection<ImageDto> findImageByAccountId(Integer accountId);

	/**
	 * Returns an image by name and accountId.
	 * @param name
	 * @param accountId
	 * @return ImageDto
	 */
	ImageDto findImageByNameAndAccountId(String name,
			Integer accountId);
	
}
