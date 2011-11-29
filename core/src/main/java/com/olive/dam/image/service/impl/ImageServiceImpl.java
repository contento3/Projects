package com.olive.dam.image.service.impl;

import com.olive.dam.image.dao.ImageDao;
import com.olive.dam.image.dto.ImageDto;
import com.olive.dam.image.model.Image;
import com.olive.dam.image.service.ImageAssembler;
import com.olive.dam.image.service.ImageService;

public class ImageServiceImpl implements ImageService {

	private ImageAssembler imageAssembler;
	private ImageDao imageDao;
	
	public ImageServiceImpl(final ImageAssembler imageAssembler,final ImageDao imageDao){
		this.imageAssembler = imageAssembler;
		this.imageDao = imageDao;
	}
	
	public ImageDto findImageById(final Integer imageId){
		return imageAssembler.domainToDto(imageDao.findById(imageId));
	}

	@Override
	public void create(ImageDto imageDto) {
		Image image = imageAssembler.dtoToDomain(imageDto);
		imageDao.persist(image);
	}

}
