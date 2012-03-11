package com.contento3.dam.image.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.image.dao.ImageDao;
import com.contento3.dam.image.model.Image;

public class ImageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Image,String> implements ImageDao{

	public ImageDaoHibernateImpl() {
		super(Image.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Image> findByAccountId(final Integer accountId){
		Criteria criteria = this.getSession()
								.createCriteria(Image.class)
								.createCriteria("account")
								.add(Restrictions
								.eq("accountId", accountId));
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public Image findByNameAndAccountId(final String name,final Integer accountId){
		Criteria criteria = this.getSession()
								.createCriteria(Image.class)
								.add(Restrictions
								.eq("name", name))
								.createCriteria("account")
								.add(Restrictions
								.eq("accountId", accountId));
		
	
		Image image = null; 
		List <Image> imageList = criteria.list();	
		if (!CollectionUtils.isEmpty(imageList)){
			image = imageList.get(0);
		}
		
		return image;
	}
	
}
