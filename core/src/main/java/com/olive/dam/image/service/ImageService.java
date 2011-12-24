package com.olive.dam.image.service;

import com.olive.common.service.Service;
import com.olive.dam.image.dto.ImageDto;

public interface ImageService extends Service<ImageDto> {

	ImageDto findImageById(Integer imageId);
	
}
