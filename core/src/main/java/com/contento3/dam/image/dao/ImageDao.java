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

}
