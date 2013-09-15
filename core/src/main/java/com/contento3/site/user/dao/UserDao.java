package com.contento3.site.user.dao;

import com.contento3.common.dao.GenericDao;
import com.contento3.site.user.model.User;

public interface UserDao extends GenericDao<User,Integer> {

	User findByUsernameAndSiteId(String username,Integer siteId);
}
