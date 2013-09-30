package com.contento3.security.role.dao.impl;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.Validate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.util.CollectionUtils;

import com.contento3.common.spring.dao.GenericDaoSpringHibernateTemplate;
import com.contento3.security.role.dao.RoleDao;
import com.contento3.security.role.model.Role;
public class RoleDaoImpl extends GenericDaoSpringHibernateTemplate <Role,Integer>
implements RoleDao{

	public RoleDaoImpl() {
		super(Role.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	@SuppressWarnings("unchecked")
	public Collection<Role> findRolesByAccountId(Integer accountId) {
		// TODO Auto-generated method stub
		Validate.notNull(accountId,"accountId cannot be null");
		Criteria criteria = this.getSession().createCriteria(Role.class).add(Restrictions.eq("account.accountId", accountId));
		return criteria.list();
	}
	@SuppressWarnings("unchecked")
	@Override
	public Role findRoleByAccountId(Integer accountId, String rolename) {
		// TODO Auto-generated method stub
		Validate.notNull(accountId,"accountId cannot be null");
		Validate.notNull(rolename ,"rolename cannot be null");
		
		final Criteria criteria = this.getSession()
				.createCriteria(Role.class)
				.add(Restrictions.eq("account.accountId", accountId))
				.add(Restrictions.eq("roleName", rolename));
		
		Role role = null;
		final List <Role> roleList = criteria.list();
		
		if (!CollectionUtils.isEmpty(roleList)){
			role = roleList.get(0);
		}
		
		return role;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Role findByRolename(String rolename) {
		// TODO Auto-generated method stub
		Validate.notNull(rolename,"rolename cannot be null");
		
		Criteria criteria = this.getSession()
				.createCriteria(Role.class)
				.add(Restrictions
				.eq("roleName", rolename));
		Role role = null;
		
		
		if (!CollectionUtils.isEmpty(criteria.list())){
			role = (Role) criteria.list().get(0);
		}
		
		return role;
	}



	public Role findById(int id) {
		// TODO Auto-generated method stub
		Validate.notNull(id,"id cannot be null");
		
		Criteria criteria = this.getSession()
				.createCriteria(Role.class)
				.add(Restrictions
				.eq("roleid", id));
		Role role = null;
		
		
		if (!CollectionUtils.isEmpty(criteria.list())){
			role = (Role) criteria.list().get(0);
		}
		
		return role;
	}
}
