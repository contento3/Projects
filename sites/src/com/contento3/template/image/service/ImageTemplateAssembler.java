package com.contento3.template.image.service;

import java.util.ArrayList;
import java.util.Collection;

import com.contento3.cms.article.dto.ArticleDto;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.template.article.dto.ArticleTemplateDto;
import com.contento3.template.image.dto.ImageTemplateDto;

public class ImageTemplateAssembler {

	/**
	 * Assemble a {@link ImageDto}  
	 * into freemarker template dto {@link ImageTemplateDto} for Image
	 * @param imageDto
	 * @return ImageTemplateDto
	 */
	public ImageTemplateDto assemble(final ImageDto imageDto){
		final ImageTemplateDto dto = new ImageTemplateDto();
		dto.setImageId(imageDto.getId());
		dto.setImageName(imageDto.getName());
		return dto;
	}

	/**
	 * Assemble a {@link Collection} of {@link ArticleDto} we get from core services 
	 * into freemarker template dto {@link ArticleTemplateDto} for Article
	 * @param articleDtos
	 * @return Collection<ArticleTemplateDto>
	 */
	public Collection<ImageTemplateDto> assemble(final Collection <ImageDto> imageDtos){
		final Collection <ImageTemplateDto> dtos = new ArrayList<ImageTemplateDto>();
		for (ImageDto image : imageDtos){
			dtos.add(assemble(image));
		}
		return dtos;
	}

}
