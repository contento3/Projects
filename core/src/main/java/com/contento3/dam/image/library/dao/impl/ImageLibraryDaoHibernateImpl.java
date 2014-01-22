package com.contento3.dam.image.library.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.image.library.dao.ImageLibraryDao;
import com.contento3.dam.image.library.model.ImageLibrary;

public class ImageLibraryDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<ImageLibrary, Integer> implements ImageLibraryDao {
	
	public ImageLibraryDaoHibernateImpl() {
		super(ImageLibrary.class);
	}

	@Override
	public Collection<ImageLibrary> findImageLibraryByAccountId(
			final Integer accountId) {
		Validate.notNull(accountId ,"accountId cannot be null");
		Criteria criteria = this.getSession()
				.createCriteria(ImageLibrary.class)
				.add(Restrictions.eq("account.accountId", accountId));
		return criteria.list();
	}
	
	
	
	
}
