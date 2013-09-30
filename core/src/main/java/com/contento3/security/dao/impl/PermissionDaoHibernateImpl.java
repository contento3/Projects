package com.contento3.security.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.dao.PermissionDao;
import com.contento3.security.model.Permission;

/**
 * Data access layer for Permission entity. 
 * @author hammad.afridi
 *
 */
public class PermissionDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<Permission,Integer> 
	 implements PermissionDao {

	public static final String CACHE_REGION = "com.contento3.security.model.Permission";

	public PermissionDaoHibernateImpl() {
		super(Permission.class);
	}

	@Override
	public Collection <Permission> findByRole(final Integer accountId,final Integer role){
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(role,"role cannot be null");
		
//		Criteria criteria = this.getSession()
//		.createCriteria(Permission.class)
//		.setCacheable(true)
//		.setCacheRegion(CACHE_REGION)
//		.add(Restrictions
//		.eq("siteId", siteId))
//		.add(Restrictions
//				.eq("IS_ENABLED", 1));
//		return criteria.list();
//	
		return null;
		}
}
