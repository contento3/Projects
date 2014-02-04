package com.contento3.security.user.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.group.dao.GroupDao;
import com.contento3.security.group.model.Group;
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
	
	private GroupDao groupDao;
	
	/**
	 * SaltedUserAssembler
	 */
	private SaltedHibernateUserAssembler userAssembler;
	
	public SaltedHibernateUserServiceImpl(final SaltedHibernateUserDao saltedHibernateUserDao,final SaltedHibernateUserAssembler saltedHibernateUserAssembler,final GroupDao groupDao) {
		Validate.notNull(saltedHibernateUserDao,"saltedHibernateUserDao cannot be null");
		Validate.notNull(saltedHibernateUserAssembler,"saltedHibernateUserAssembler cannot be null");
		Validate.notNull(groupDao,"groupDao cannot be null");
		
		this.userDao = saltedHibernateUserDao;
		this.userAssembler = saltedHibernateUserAssembler;
		this.groupDao = groupDao;
	}
	@RequiresPermissions("add:user")
	@Override
	public Integer create(final SaltedHibernateUserDto dto)
			throws EntityAlreadyFoundException, EntityNotCreatedException {
		Validate.notNull(dto,"dto cannot be null");
		final SaltedHibernateUser user = userAssembler.dtoToDomain(dto);
		final RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
		final String salt = saltGenerator.nextBytes().toBase64();
		final ByteSource originalPassword = ByteSource.Util.bytes(dto.getPassword());
		
		final Hash hash = new Sha256Hash(originalPassword, salt, 1);
		final String finalHash = hash.toString();
			
		user.setPassword(finalHash);
		user.setSalt(salt);
		Integer userId = userDao.persist(user);
		
		if (null==userId){
			throw new EntityNotCreatedException();
		}
		return userId;
	}
	
	@Override
	public void delete(final SaltedHibernateUserDto dtoToDelete) throws EntityCannotBeDeletedException {
		//Check the user if it is associated to a groups
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		Collection <Group> associatedGroups = groupDao.findByUserId(dtoToDelete.getId());
		
		if (CollectionUtils.isEmpty(associatedGroups)){
			userDao.delete(userAssembler.dtoToDomain(dtoToDelete));
		}
		else {
			throw new EntityCannotBeDeletedException("User with id ["+ dtoToDelete.getId() +"]" + "cannot be deleted.");
		} 
	}
	public Collection<SaltedHibernateUserDto> findUsersByAccountId(final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		return userAssembler.domainsToDtos(userDao.findUsersByAccountId(accountId));
	}
	@Override
	public SaltedHibernateUserDto findUserById(final Integer userId) {
		Validate.notNull(userId,"userId cannot be null");
		return userAssembler.domainToDto(userDao.findById(userId));
	}
	@Override
	public SaltedHibernateUserDto findUserByUsername(final String username) {
		Validate.notNull(username,"username cannot be null");
		return userAssembler.domainToDto(userDao.findByUsername(username));
	}
	@Override
	public void update(SaltedHibernateUserDto dtoToUpdate) {
		Validate.notNull(dtoToUpdate,"dtoToUpdate cannot be null");
		userDao.update( userAssembler.dtoToDomain(dtoToUpdate) );
	}
	
}
