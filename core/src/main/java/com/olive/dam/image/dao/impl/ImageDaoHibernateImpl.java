package com.olive.dam.image.dao.impl;

import com.olive.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.olive.dam.image.dao.ImageDao;
import com.olive.dam.image.model.Image;

public class ImageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Image,Integer> implements ImageDao{

	public ImageDaoHibernateImpl() {
		super(Image.class);
	}

}
