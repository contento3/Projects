package com.contento3.security.role.dao;
import java.util.Collection;

import com.contento3.common.dao.GenericDao;
import com.contento3.security.role.model.Role;

public interface RoleDao extends GenericDao<Role, Integer>{
	
	Collection<Role> findRolesByAccountId(final Integer accountId);
	Role findRoleByAccountId(Integer accountId, String rolename);
	Role findByRolename(String rolename);
   

}
