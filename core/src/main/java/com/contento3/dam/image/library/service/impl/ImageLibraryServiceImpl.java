package com.contento3.dam.image.library.service.impl;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ImageLibraryServiceImpl implements ImageLibraryService  {

	public ImageLibraryServiceImpl() {
		
	}
	
	@Override
	public void create(final ImageLibraryDto dto) throws EntityAlreadyFoundException {
		
	}

}
