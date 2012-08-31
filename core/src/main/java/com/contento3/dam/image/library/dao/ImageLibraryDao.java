package com.contento3.dam.image.library.dao;

import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.dam.image.library.model.ImageLibrary;

public interface ImageLibraryDao extends GenericDao<ImageLibrary, Integer>{	
	/**
	 * Return collection of image libraries which has account id
	 * @param accountId
	 * @return
	 */
	Collection<ImageLibrary> findImageLibraryByAccountId(Integer accountId);
}
