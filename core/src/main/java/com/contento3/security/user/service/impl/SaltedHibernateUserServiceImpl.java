package com.contento3.security.user.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.Collection;

import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.model.SaltedHibernateUser;
import com.contento3.security.user.service.SaltedHibernateUserAssembler;
import com.contento3.security.user.service.SaltedHibernateUserService;

public class SaltedHibernateUserServiceImpl implements SaltedHibernateUserService{

	/**
	 * SaltedUser data access layer
	 */
	private  SaltedHibernateUserDao userDao;
	
	/**
	 * SaltedUserAssembler
	 */
	private SaltedHibernateUserAssembler userAssembler;
	
	/**
	 * Used in generating salt
	 */
	private byte[] seed = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
	
	public SaltedHibernateUserServiceImpl(final SaltedHibernateUserDao saltedHibernateUserDao,final SaltedHibernateUserAssembler saltedHibernateUserAssembler) {
		this.userDao = saltedHibernateUserDao;
		this.userAssembler = saltedHibernateUserAssembler;
	}

	@Override
	public String create(final SaltedHibernateUserDto dto)
			throws EntityAlreadyFoundException, EntityNotCreatedException {
		final SaltedHibernateUser user = userAssembler.dtoToDomain(dto);
		final RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
		final String salt = saltGenerator.nextBytes().toBase64();
		final ByteSource originalPassword = ByteSource.Util.bytes(dto.getPassword());
		
		final Hash hash = new Sha256Hash(originalPassword, salt, 1);
		final String finalHash = hash.toString();
			
		user.setPassword(finalHash);
		user.setSalt(salt);
		String username = userDao.persist(user);
		
		if (null==username){
			throw new EntityNotCreatedException();
		}
		return username;
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
