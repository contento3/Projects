package com.contento3.security.group.dao.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.model.Group;

public class GroupDaoHibernateImpl extends GenericDaoSpringHibernateTemplate <Group,Integer>
implements GroupDao {
	
	GroupDaoHibernateImpl(){
		super(Group.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Group findByGroupName(final String groupName)
	{
		Validate.notNull(groupName,"groupName cannot be null");
		final Criteria criteria = this.getSession()
		.createCriteria(Group.class)
		.add(Restrictions
		.eq("name", groupName));
		
		Group group = null;
		if (!CollectionUtils.isEmpty(criteria.list())) {
			group = (Group) criteria.list().get(0);
		}
		
		return group;
		
	}

	@Override
	public Collection<Group> findByUserId(final Integer userid) {
		Validate.notNull(userid,"userid cannot be null");
		final Criteria criteria = this.getSession()
		.createCriteria(Group.class)
		.createAlias("members", "member")
		.add(Restrictions.eq("member.userId", userid));
		
		return criteria.list();
	}
	
}
