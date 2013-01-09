package com.contento3.security.user.service.impl;

import java.util.Collection;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.model.SaltedHibernateUser;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.util.AlphaNumericStringGenerator;

public class SaltedHibernateUserServiceImpl implements SaltedHibernateUserService{

	/**
	 * SaltedUser data access layer
	 */
	private  SaltedHibernateUserDao userDao;
	
	/**
	 * SaltedUserAssembler
	 */
	private SaltedHibernateUserAssembler userAssembler;
	
	public SaltedHibernateUserServiceImpl(final SaltedHibernateUserDao saltedHibernateUserDao,final SaltedHibernateUserAssembler saltedHibernateUserAssembler) {
		this.userDao = saltedHibernateUserDao;
		this.userAssembler = saltedHibernateUserAssembler;
	}

	@Override
	public SaltedHibernateUser create(final SaltedHibernateUserDto dto)
			throws EntityAlreadyFoundException {
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final SaltedHibernateUser user = userAssembler.dtoToDomain(dto);
	    user.setSalt(AlphaNumericStringGenerator.generateString(9));
		user.setPassword(encoder.encodePassword(AlphaNumericStringGenerator.generateString(7), user.getSalt()));
	    userDao.persist(user);
		return user;
	}

	@Override
	public void delete(final SaltedHibernateUserDto dtoToDelete) {
		userDao.delete(userAssembler.dtoToDomain(dtoToDelete));
	}
	
	public Collection<SaltedHibernateUserDto> findUsersByAccountId(final Integer accountId) {
		return userAssembler.domainsToDtos(userDao.findUsersByAccountId(accountId));
	}

	@Override
	public SaltedHibernateUserDto findUserByName(final String name) {
		return userAssembler.domainToDto(userDao.findById(name));
	}

	
}
