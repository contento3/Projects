package com.contento3.security.user.service.impl;

import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;
import com.contento3.security.user.service.SaltedHibernateUserService;

public class SaltedHibernateUserServiceImpl implements SaltedHibernateUserService{

	private  SaltedHibernateUserDao userDao;
	private SaltedHibernateUserAssembler userAssembler;
	
	public SaltedHibernateUserServiceImpl(final SaltedHibernateUserDao saltedHibernateUserDao,final SaltedHibernateUserAssembler saltedHibernateUserAssembler) {
		this.userDao = saltedHibernateUserDao;
		this.userAssembler = saltedHibernateUserAssembler;
	}
	@Override
	public Object create(final SaltedHibernateUserDto dto)
			throws EntityAlreadyFoundException {
		
		return null;
	}

	@Override
	public void delete(final SaltedHibernateUserDto dtoToDelete) {
		// TODO Auto-generated method stub
	}
	
	public Collection<SaltedHibernateUserDto> findUsersByAccountId(final Integer accountId) {
		return userAssembler.domainsToDtos(userDao.findUsersByAccountId(accountId));
	}

	@Override
	public SaltedHibernateUserDto findUserByName(final String name) {
		return userAssembler.domainToDto(userDao.findById(name));
	}

}
