package com.contento3.dam.image.library.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.image.library.dao.ImageLibraryDao;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.service.ImageLibraryAssembler;
import com.contento3.dam.image.library.service.ImageLibraryService;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ImageLibraryServiceImpl implements ImageLibraryService  {

	private ImageLibraryDao imageLibraryDao;
	private ImageLibraryAssembler imageLibraryAssembler;
	
	public ImageLibraryServiceImpl(final ImageLibraryDao imageLibraryDao,final ImageLibraryAssembler imageLibraryAssembler) {
		Validate.notNull(imageLibraryDao,"imageLibraryDao cannot be null");
		Validate.notNull(imageLibraryAssembler,"imageLibraryAssembler cannot be null");
		
		this.imageLibraryDao = imageLibraryDao;
		this.imageLibraryAssembler = imageLibraryAssembler;
	}
	
	@Override
	public Integer create(final ImageLibraryDto dto) throws EntityAlreadyFoundException {
		Validate.notNull(dto,"dto cannot be null");
		return imageLibraryDao.persist(imageLibraryAssembler.dtoToDomain(dto));
	}

	@Override
	public Collection<ImageLibraryDto> findImageLibraryByAccountId(
			Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		return imageLibraryAssembler.domainsToDtos(imageLibraryDao.findImageLibraryByAccountId(accountId));
	}

	@Override
	public ImageLibraryDto findImageLibraryById(Integer imageLibraryId) {
		Validate.notNull(imageLibraryId,"imageLibraryId cannot be null");
		return imageLibraryAssembler.domainToDto(imageLibraryDao.findById(imageLibraryId));
	}

	@Override
	public void delete(ImageLibraryDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}

}
