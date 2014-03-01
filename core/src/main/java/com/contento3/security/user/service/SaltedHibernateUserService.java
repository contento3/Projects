package com.contento3.security.user.service;

import java.util.Collection;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.service.SimpleService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;

public interface SaltedHibernateUserService extends SimpleService<SaltedHibernateUserDto> {

	/**
	 * Find user by account id
	 * @param accountId
	 * @return
	 */
	Collection<SaltedHibernateUserDto> findUsersByAccountId(final Integer accountId);
	
	/**
	 * Find user by name
	 * @param name
	 * @return
	 */
	SaltedHibernateUserDto findUserById(final Integer id);

	/**
	 * Find user by name
	 * @param name
	 * @return
	 */
	SaltedHibernateUserDto findUserByUsername(final String username);

	/**
	 * Updates the SalterHibernateUserDto
	 * @param SalterHibernateUserDto
	 * @return void
	 * @throws EntityAlreadyFoundException 
	 */
	public void update(SaltedHibernateUserDto dtoToUpdate) throws EntityAlreadyFoundException;
}
