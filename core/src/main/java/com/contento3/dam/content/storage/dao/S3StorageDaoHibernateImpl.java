package com.contento3.dam.content.storage.dao;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.dam.content.storage.S3Storage;

public class S3StorageDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<S3Storage, Integer> implements S3StorageDao {

	public S3StorageDaoHibernateImpl() {
		super(S3Storage.class);
	}

	@Override
	public S3Storage findByAccountId(final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");

		Criteria criteria = this.getSession()
		.createCriteria(S3Storage.class)
		.add(Restrictions.eq("account.accountId", accountId));

		S3Storage storage = null;
		if (!CollectionUtils.isEmpty(criteria.list()))
			storage = (S3Storage)criteria.list().get(0);
		return storage;
	}

}
