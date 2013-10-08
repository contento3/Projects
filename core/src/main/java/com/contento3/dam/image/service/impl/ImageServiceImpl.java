package com.contento3.dam.image.service.impl;

import java.util.Collection;
import java.util.UUID;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.account.dao.AccountDao;
import com.contento3.account.model.Account;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.dam.image.dao.ImageDao;
import com.contento3.dam.image.dto.ImageDto;
import com.contento3.dam.image.model.Image;
import com.contento3.dam.image.service.ImageAssembler;
import com.contento3.dam.image.service.ImageService;
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ImageServiceImpl implements ImageService {

	private ImageAssembler imageAssembler;
	private ImageDao imageDao;
	private AccountDao accountDao;
	
	public ImageServiceImpl(final ImageAssembler imageAssembler,final ImageDao imageDao,final AccountDao accountDao){
		Validate.notNull(imageAssembler,"imageAssembler cannot be null");
		Validate.notNull(imageDao,"imageDao cannot be null");
		Validate.notNull(accountDao,"accountDao cannot be null");
		
		this.imageAssembler = imageAssembler;
		this.imageDao = imageDao;
		this.accountDao = accountDao;
	}
	
	public ImageDto findImageByUuid(final String imageId){
		Validate.notNull(imageId,"imageId cannot be null");
		return imageAssembler.domainToDto(imageDao.findByUuid(imageId));
	}

	@Override
	public Collection<ImageDto> findImageByAccountId(final Integer accountId){
		Validate.notNull(accountId,"accountId cannot be null");
		return imageAssembler.domainsToDtos(imageDao.findByAccountId(accountId));
	}

	@Override
	public ImageDto findImageByNameAndAccountId(final String name,final Integer accountId) throws EntityNotFoundException{
		Validate.notNull(name,"name cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		final Image image = imageDao.findByNameAndAccountId(name, accountId);
		if (null==image){
			throw new EntityNotFoundException("Image with name ["+name+"]"+"not found");
		}
		
		return imageAssembler.domainToDto(image);
	}

	@Override
	public Integer create(final ImageDto imageDto) {
		Validate.notNull(imageDto,"imageDto cannot be null");
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
		Validate.notNull(siteId,"siteId cannot be null");
		Validate.notNull(count,"count cannot be null");
		
		return imageAssembler.domainsToDtos(imageDao.findLatestImagesBySiteId(siteId, count));
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ImageDto> findImagesByLibrary(final Integer libraryId) {
		Validate.notNull(libraryId,"libraryId cannot be null");
		return imageAssembler.domainsToDtos(imageDao.findImagesByLibrary(libraryId));
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final ImageDto imageDto) {
		Validate.notNull(imageDto,"imageDto cannot be null");
		Image image = imageAssembler.dtoToDomain(imageDto);
		Account account = accountDao.findById(imageDto.getAccountDto().getAccountId());
		image.setAccount(account);
		imageDao.update(image);
	}

	@Override
	public void delete(ImageDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ImageDto findById(Integer imageId){
		Validate.notNull(imageId,"imageId cannot be null");
		return this.imageAssembler.domainToDto(imageDao.findById(imageId));
	}

}
