package com.contento3.dam.image.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.image.dao.ImageDao;
import com.contento3.dam.image.model.Image;

public class ImageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Image,Integer> implements ImageDao{

	public ImageDaoHibernateImpl() {
		super(Image.class);
	}

	@Override
	public Collection<Image> findByAccountId(Integer accountId){
		Criteria criteria = this.getSession()
								.createCriteria(Image.class)
								.createCriteria("account")
								.add(Restrictions
								.eq("accountId", accountId));
		return criteria.list();
	}

}
