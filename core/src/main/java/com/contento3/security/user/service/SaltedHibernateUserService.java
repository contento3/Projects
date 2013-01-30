package com.contento3.security.user.service;

import java.util.Collection;

import com.contento3.common.service.Service;
import com.contento3.security.user.dto.SaltedHibernateUserDto;

public interface SaltedHibernateUserService extends Service<SaltedHibernateUserDto> {

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
	SaltedHibernateUserDto findUserByName(final String name);

	/**
	 * Updates the SalterHibernateUserDto
	 * @param SalterHibernateUserDto
	 * @return void
	 */
	public void update(SaltedHibernateUserDto dtoToUpdate);
}
