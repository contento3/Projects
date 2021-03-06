package com.contento3.dam.image.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.contento3.storage.manager.StorageManager;
@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ImageServiceImpl implements ImageService {

	/**
	 * Assembler that assembles the image to image dto and vice versa
	 */
	private ImageAssembler imageAssembler;
	
	/**
	 * Dao that fetches the data for image entity
	 */
	private ImageDao imageDao;
	
	/**
	 * Account that image is associated with
	 */
	private AccountDao accountDao;
	
	/**
	 * Storage manager that stores the image based on selected library
	 */
	private StorageManager storageManager;
	
	public ImageServiceImpl(final ImageAssembler imageAssembler,final ImageDao imageDao,final AccountDao accountDao,
			final StorageManager storageManager){
		Validate.notNull(imageAssembler,"imageAssembler cannot be null");
		Validate.notNull(imageDao,"imageDao cannot be null");
		Validate.notNull(accountDao,"accountDao cannot be null");
		Validate.notNull(storageManager,"storageManager cannot be null");
		
		this.imageAssembler = imageAssembler;
		this.imageDao = imageDao;
		this.accountDao = accountDao;
		this.storageManager = storageManager;
	}
	
	@RequiresPermissions("IMAGE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ImageDto findImageByUuid(final String imageId){
		Validate.notNull(imageId,"imageId cannot be null");
		return imageAssembler.domainToDto(imageDao.findByUuid(imageId));
	}

	@RequiresPermissions("IMAGE:VIEW_LISTING")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ImageDto> findImageByAccountId(final Integer accountId){
		Validate.notNull(accountId,"accountId cannot be null");
		return imageAssembler.domainsToDtos(imageDao.findByAccountId(accountId));
	}

	@RequiresPermissions("IMAGE:VIEW")
	@Override
	public ImageDto findImageByNameAndAccountId(final String name,final Integer accountId) throws EntityNotFoundException{
		Validate.notNull(name,"name cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		final Image image = imageDao.findByNameAndAccountId(name, accountId);
		if (null==image){
			throw new EntityNotFoundException("Image with name " + name  + " not found.");
		}
		
		return imageAssembler.domainToDto(image);
	}

	@RequiresPermissions("IMAGE:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Boolean create(final ImageDto imageDto) {
		Validate.notNull(imageDto,"imageDto cannot be null");
		Image image = imageAssembler.dtoToDomain(imageDto);
		Account account = accountDao.findById(imageDto.getAccountDto().getAccountId());
		image.setAccount(account);
		
		//Pass the image to storage manager and 
		//it will handle where to store the content.
		return storageManager.addToStorage(image);
	}
	
	@RequiresPermissions("IMAGE:VIEW_LISTING")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ImageDto> findLatestImagesBySiteId(Integer siteId,
			Integer count) {
		Validate.notNull(siteId,"siteId cannot be null");
		Validate.notNull(count,"count cannot be null");
		
		return imageAssembler.domainsToDtos(imageDao.findLatestImagesBySiteId(siteId, count));
	}

	@RequiresPermissions("IMAGE:VIEW_LISTING")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<ImageDto> findImagesByLibrary(final Integer libraryId) {
		Validate.notNull(libraryId,"libraryId cannot be null");
		return imageAssembler.domainsToDtos(imageDao.findImagesByLibrary(libraryId));
	}

	@RequiresPermissions("IMAGE:EDIT")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(final ImageDto imageDto) {
		Validate.notNull(imageDto,"imageDto cannot be null");
		Image image = imageAssembler.dtoToDomain(imageDto);
		Account account = accountDao.findById(imageDto.getAccountDto().getAccountId());
		image.setAccount(account);
		imageDao.update(image);
	}

	@RequiresPermissions("IMAGE:DELETE")
	@Override
	public void delete(ImageDto dtoToDelete) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		imageDao.delete(imageAssembler.dtoToDomain(dtoToDelete));
	}

	@RequiresPermissions("IMAGE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ImageDto findById(Integer imageId){
		Validate.notNull(imageId,"imageId cannot be null");
		return this.imageAssembler.domainToDto(imageDao.findById(imageId));
	}

	@RequiresPermissions("IMAGE:CROP")
	@Override
	public ImageDto crop(final ImageDto imageToCrop) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequiresPermissions("IMAGE:RESIZE")
	@Override
	public ImageDto resize(final ImageDto imageToResize) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequiresPermissions("IMAGE:ROTATE")
	@Override
	public ImageDto rotate(final ImageDto imageToRotate) {
		// TODO Auto-generated method stub
		return null;
	}

	@RequiresPermissions("IMAGE:VIEW_LISTING")
	@Override
	public Collection<ImageDto> findImagesByLibraryAndAccountId(
			Integer libraryId, Integer accountId) {
		Validate.notNull(libraryId,"libraryId cannot be null");
		Validate.notNull(accountId,"accountId cannot be null");
		return imageAssembler.domainsToDtos(imageDao.findImagesByLibraryAndAccount(libraryId, accountId));
	}

	@RequiresPermissions("IMAGE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public ImageDto findImageByIdAndSiteId(Integer imageId, Integer siteId) {
		
		Validate.notNull(siteId,"siteId cannot be null");
		 Image image = imageDao.findImageByIdAndSiteId(imageId, siteId);
		 ImageDto imageDto = null;
			if (image != null){
				imageDto = imageAssembler.domainToDto(image);
			}
		return imageDto;	
	}	

}
