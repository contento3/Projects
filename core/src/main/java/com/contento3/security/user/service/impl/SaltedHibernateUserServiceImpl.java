package com.contento3.security.user.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.common.security.PasswordInfo;
import com.contento3.common.security.PasswordUtility;
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

	@RequiresPermissions("USER:ADD")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(final SaltedHibernateUserDto dto)
			throws EntityAlreadyFoundException, EntityNotCreatedException {
		Validate.notNull(dto,"dto cannot be null");

		final SaltedHibernateUser userExist = userDao.findUserByAccountId(dto.getAccount().getAccountId(),dto.getUserName());
		Integer userId = null;
		
		if (null==userExist){
			final SaltedHibernateUser user = userAssembler.dtoToDomain(dto);
			final PasswordInfo info = PasswordUtility.createSaltedPassword(dto.getPassword());
			
			user.setPassword(info.getPassword());
			user.setSalt(info.getSalt());
			userId = userDao.persist(user);
			
			if (null==userId){
				throw new EntityNotCreatedException();
			}
		}
		else {
			throw new EntityAlreadyFoundException("User with username "+dto.getUserName()+" already exist");
		}
		return userId;
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("USER:DELETE")
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
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("USER:VIEW_LISTING")
	@Override
	public Collection<SaltedHibernateUserDto> findUsersByAccountId(final Integer accountId) {
		Validate.notNull(accountId,"accountId cannot be null");
		return userAssembler.domainsToDtos(userDao.findUsersByAccountId(accountId));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("USER:VIEW_LISTING")
	@Override
	public SaltedHibernateUserDto findUserById(final Integer userId) {
		Validate.notNull(userId,"userId cannot be null");
		return userAssembler.domainToDto(userDao.findById(userId));
	}
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("USER:VIEW_LISTING")
	@Override
	public SaltedHibernateUserDto findUserByUsername(final String username) {
		Validate.notNull(username,"username cannot be null");
		return userAssembler.domainToDto(userDao.findByUsername(username));
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@RequiresPermissions("USER:UPDATE")
	@Override
	public void update(SaltedHibernateUserDto dtoToUpdate) {
		Validate.notNull(dtoToUpdate,"dtoToUpdate cannot be null");
		userDao.update( userAssembler.dtoToDomain(dtoToUpdate) );
	}
	
}
