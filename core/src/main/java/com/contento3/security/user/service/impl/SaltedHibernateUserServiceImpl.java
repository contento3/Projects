package com.contento3.security.user.service.impl;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;

public class SaltedHibernateUserServiceImpl implements SaltedHibernateUserService{

	@Override
	public Object create(SaltedHibernateUserDto dto)
			throws EntityAlreadyFoundException {
		
		return null;
	}

	@Override
	public void delete(SaltedHibernateUserDto dtoToDelete) {
		// TODO Auto-generated method stub
		
	}

}
