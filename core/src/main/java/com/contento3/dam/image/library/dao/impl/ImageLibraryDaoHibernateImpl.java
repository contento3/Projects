package com.contento3.dam.image.library.dao.impl;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.image.library.dao.ImageLibraryDao;
import com.contento3.dam.image.library.model.ImageLibrary;

public class ImageLibraryDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<ImageLibrary, Integer> implements ImageLibraryDao {
	
	public ImageLibraryDaoHibernateImpl() {
		super(ImageLibrary.class);
	}

	
	
}
