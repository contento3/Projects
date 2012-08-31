package com.contento3.dam.image.service.impl;

import java.util.Collection;
import java.util.UUID;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.model.Account;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.dam.image.dao.ImageDao;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.model.Image;
import com.contento3.dam.image.service.ImageAssembler;
import com.contento3.dam.image.service.ImageService;
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ImageServiceImpl implements ImageService {

	private ImageAssembler imageAssembler;
	private ImageDao imageDao;
	private AccountDao accountDao;
	
	public ImageServiceImpl(final ImageAssembler imageAssembler,final ImageDao imageDao,final AccountDao accountDao){
		this.imageAssembler = imageAssembler;
		this.imageDao = imageDao;
		this.accountDao = accountDao;
	}
	
	public ImageDto findImageByUuid(final String imageId){
		return imageAssembler.domainToDto(imageDao.findByUuid(imageId));
	}

	@Override
	public Collection<ImageDto> findImageByAccountId(final Integer accountId){
		return imageAssembler.domainsToDtos(imageDao.findByAccountId(accountId));
	}

	@Override
	public ImageDto findImageByNameAndAccountId(final String name,final Integer accountId) throws EntityNotFoundException{
		final Image image = imageDao.findByNameAndAccountId(name, accountId);
		if (null==image){
			throw new EntityNotFoundException("Image with name ["+name+"]"+"not found");
		}
		
		return imageAssembler.domainToDto(image);
	}

	@Override
	public Integer create(final ImageDto imageDto) {
		Image image = imageAssembler.dtoToDomain(imageDto);
		Account account = accountDao.findById(imageDto.getAccountDto().getAccountId());
		image.setAccount(account);
		image.setImageUuid(UUID.randomUUID().toString());
		return imageDao.persist(image);
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ImageDto> findLatestImagesBySiteId(Integer siteId,
			Integer count) {
	
		return imageAssembler.domainsToDtos(imageDao.findLatestImagesBySiteId(siteId, count));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ImageDto> findImagesByLibrary(final Integer libraryId) {
		return imageAssembler.domainsToDtos(imageDao.findImagesByLibrary(libraryId));
	}

}
