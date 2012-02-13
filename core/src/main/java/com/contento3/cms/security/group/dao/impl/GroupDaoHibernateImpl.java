package com.contento3.cms.security.group.dao.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.cms.page.dao.PageDao;
import com.contento3.cms.page.model.Page;
import com.contento3.cms.security.group.dao.GroupDao;
import com.contento3.cms.security.group.model.Group;
import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;

public class GroupDaoHibernateImpl extends GenericDaoSpringHibernateTemplate <Group,String>
implements GroupDao {
	
	GroupDaoHibernateImpl(){
		super(Group.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Group> findByGroupName(String GroupName)
	{
		Criteria criteria = this.getSession()
		.createCriteria(Group.class)
		.addOrder(Order.desc("name"))
		.add(Restrictions
		.eq("name.group_name", GroupName));
		return criteria.list();
		
	}
	
}
