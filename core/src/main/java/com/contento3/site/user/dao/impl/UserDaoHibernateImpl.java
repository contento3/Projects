package com.contento3.site.user.dao.impl;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.site.user.dao.UserDao;
import com.contento3.site.user.model.User;

public class UserDaoHibernateImpl extends GenericDaoSpringHibernateTemplate<User,Integer>
implements UserDao {

	public UserDaoHibernateImpl() {
		super(User.class);
	}

	@Override
	public User findByUsernameAndSiteId(final String username,final Integer siteId) {
		Validate.notNull(siteId,"siteId cannot be null");
		
		Criteria criteria = this.getSession()
		.createCriteria(User.class)
		.add(Restrictions
		.eq("site.siteId", siteId))
		.add(Restrictions
		.eq("username", username));
		
		User user = null;
		if (!CollectionUtils.isEmpty(criteria.list())){
			user = (User)criteria.list().get(0);
		}	
		
		return user;
	}

	
	
	
}
