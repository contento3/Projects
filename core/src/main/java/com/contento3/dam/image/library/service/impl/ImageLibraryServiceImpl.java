package com.contento3.dam.image.library.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.dam.content.library.dao.ContentStorageLibraryMappingDao;
import com.contento3.dam.content.library.model.ContentStorageLibraryMapping;
import com.contento3.dam.content.storage.Storage;
import com.contento3.dam.content.storage.exception.InvalidStorageException;
import com.contento3.dam.image.library.dao.ImageLibraryDao;
import com.contento3.dam.image.library.dto.ImageLibraryDto;
import com.contento3.dam.image.library.model.ImageLibrary;
import com.contento3.dam.image.library.service.ImageLibraryAssembler;
import com.contento3.dam.image.library.service.ImageLibraryService;
import com.contento3.dam.storagetype.dao.StorageTypeDao;
import com.contento3.dam.storagetype.model.StorageType;
import com.contento3.storage.manager.StorageManager;
import com.contento3.storage.provider.StorageProviderFactory;

@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
public class ImageLibraryServiceImpl implements ImageLibraryService  {

	private ImageLibraryDao imageLibraryDao;

	private ImageLibraryAssembler imageLibraryAssembler;
	
	private StorageTypeDao storageTypeDao;
	
	private ContentStorageLibraryMappingDao contentStorageLibraryMappingDao;
	
	private StorageManager contentStorageMgr;
	
	public ImageLibraryServiceImpl(final ImageLibraryDao imageLibraryDao,final ImageLibraryAssembler imageLibraryAssembler,
			final StorageTypeDao storageTypeDao, final ContentStorageLibraryMappingDao contentStorageLibraryMappingDao,
			final StorageProviderFactory storageProviderFactory,final StorageManager contentStorageMgr) {
		
		Validate.notNull(imageLibraryDao,"imageLibraryDao cannot be null");
		Validate.notNull(imageLibraryAssembler,"imageLibraryAssembler cannot be null");
		Validate.notNull(storageTypeDao,"storageTypeDao cannot be null");
		Validate.notNull(storageProviderFactory,"storageProviderFactory cannot be null");
		Validate.notNull(contentStorageMgr,"contentStorageMgr cannot be null");
		Validate.notNull(contentStorageLibraryMappingDao,"contentStorageLibraryMappingDao cannot be null");
		
		this.storageTypeDao = storageTypeDao;
		this.imageLibraryDao = imageLibraryDao;
		this.imageLibraryAssembler = imageLibraryAssembler;
		this.contentStorageLibraryMappingDao = contentStorageLibraryMappingDao;
		this.contentStorageMgr = contentStorageMgr;
	}
	
	@Override
	public Integer create(final ImageLibraryDto dto) throws EntityAlreadyFoundException,InvalidStorageException {
		Validate.notNull(dto,"dto cannot be null");
		final Integer storageTypeId = dto.getStorageTypeId();
		final StorageType storageType = storageTypeDao.findById(storageTypeId);
		final ImageLibrary imageLibrary = imageLibraryAssembler.dtoToDomain(dto);

		final String contentType = dto.getContentType();
		Integer libraryId;
		if (isUniqueLibrary(imageLibrary,contentType)){

			final Storage storage = contentStorageMgr.fetchContentStorage(storageType.getName(),imageLibrary.getAccount().getAccountId());
			
			final ContentStorageLibraryMapping cslm = new ContentStorageLibraryMapping();
			cslm.setContentType(contentType);
			cslm.setLibrary(imageLibrary);
			cslm.setStorageType(storageType);
			libraryId = imageLibraryDao.persist(imageLibrary);
			
			if (!storageType.getName().equals("DATABASE") && null==storage){
				throw new InvalidStorageException("Storage configuration not found for storagetype "+storageType);
			}
			
			//In case of storagetype as Database this will be null
			if (null!=storage){
				cslm.setStorageId(storage.getId());
			}
			
			contentStorageLibraryMappingDao.persist(cslm);
		}
		else {
			throw new EntityAlreadyFoundException(String.format("Library for %s with libary name [%s] already account for this account",contentType,imageLibrary.getName()));
		}
		return libraryId;
	}
	
	private boolean isUniqueLibrary(final ImageLibrary library,final String contentType){
		if (null!=contentStorageLibraryMappingDao.findByLibraryAndContentType(library.getName(), contentType, library.getAccount().getAccountId())){
			return false;
		}
		return true;
	}

	@Override
	public Collection<ImageLibraryDto> findImageLibraryByAccountId(
			final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		return imageLibraryAssembler.domainsToDtos(imageLibraryDao.findImageLibraryByAccountId(accountId));
	}

	@Override
	public ImageLibraryDto findImageLibraryById(final Integer imageLibraryId) {
		Validate.notNull(imageLibraryId,"imageLibraryId cannot be null");
		return imageLibraryAssembler.domainToDto(imageLibraryDao.findById(imageLibraryId));
	}

	@Override
	public void delete(final ImageLibraryDto dtoToDelete) {
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		if (dtoToDelete.getContentType().equals("IMAGE")){
			
		}
		
		final ContentStorageLibraryMapping contentStorageLibraryMapping = contentStorageLibraryMappingDao.findByLibraryId(dtoToDelete.getId(), dtoToDelete.getAccountDto().getAccountId());
		contentStorageLibraryMappingDao.delete(contentStorageLibraryMapping);
		imageLibraryDao.delete(contentStorageLibraryMapping.getLibrary());
	}

}
