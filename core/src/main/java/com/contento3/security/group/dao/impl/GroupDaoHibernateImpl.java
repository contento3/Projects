package com.contento3.security.group.dao.impl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.model.Group;

public class GroupDaoHibernateImpl extends GenericDaoSpringHibernateTemplate <Group,String>
implements GroupDao {
	
	GroupDaoHibernateImpl(){
		super(Group.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Collection<Group> findByGroupName(String groupName)
	{
		Criteria criteria = this.getSession()
		.createCriteria(Group.class)
		.add(Restrictions
		.eq("name", groupName));
		return criteria.list();
		
	}
	
}
