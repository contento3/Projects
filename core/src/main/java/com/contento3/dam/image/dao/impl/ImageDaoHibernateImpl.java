package com.contento3.dam.image.dao.impl;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.image.dao.ImageDao;
import com.contento3.dam.image.model.Image;

public class ImageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Image,String> implements ImageDao{

	public ImageDaoHibernateImpl() {
		super(Image.class);
	}

}
