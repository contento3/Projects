package com.contento3.dam.image.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.image.model.Image;

public interface ImageDao extends GenericDao<Image,Integer>{

	/**
	 * Returns a {@link java.util.Collection} of image for an account
	 * @param accountId
	 * @return
	 */
	Collection<Image> findByAccountId(Integer accountId);

	/**
	 * Returns the {@link com.contento3.dam.image.model.Image} by name for a particular 
	 * {@link com.contento3.account.model.Account}
	 * @param name
	 * @param accountId
	 * @return 
	 */
	Image findByNameAndAccountId(String name,Integer accountId);
	
	/**
	 * return  latest images by site id and count is number of rows 
	 * @param siteId
	 * @param count
	 * @return
	 */
	
	Collection<Image> findLatestImagesBySiteId(Integer siteId,Integer count);
	
	/**
	 * Return a {@link java.util.Collection} of image by library
	 * @param imageId
	 * @return
	 */
	Collection<Image> findImagesByLibrary(Integer libraryId);

	/**
	 * Returns the {@link com.contento3.dam.image.model.Image} 
	 * for a particular uuid
	 * @param uuid
	 * @return 
	 */
	Image findByUuid(String uuid);

	

}
