package com.contento3.site.user.service.impl;

import org.apache.commons.lang.Validate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.site.user.dao.UserDao;
import com.contento3.site.user.dto.UserDto;
import com.contento3.site.user.model.User;
import com.contento3.site.user.service.UserRegistrationService;

public class UserRegistrationServiceImpl implements UserRegistrationService {

	private UserDao userDao;
	
	private PasswordEncoder passwordEncoder;

	private SiteDAO siteDao;
	
	@Override
	public Integer create(UserDto dto) throws EntityAlreadyFoundException {
		Validate.notNull(dto,"dto cannot be null");
		
		String password = dto.getPassword();
		passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
			
		User user = new User();
		user.setPassword(hashedPassword);
		user.setUsername(dto.getUsername());
		user.setPasswordReminder(dto.getPasswordReminder());
		user.setEnabled(true);
		
		return userDao.persist(user);
	}
	
	public void create(final UserDto userDto,final String domain){
		Validate.notNull(userDto,"articleImageDao cannot be null");
		Validate.notNull(domain,"articleImageDao cannot be null");
		
		final Site site = siteDao.findByDomain(domain);
		
		String password = userDto.getPassword();
		passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(password);
			
		User user = new User();
		user.setPassword(hashedPassword);
		user.setUsername(userDto.getUsername());
		user.setPasswordReminder(userDto.getPasswordReminder());
		user.setEnabled(true);
		user.setSite(site);
		userDao.persist(user);
	}

	public void setUserDao(final UserDao userDao){
		Validate.notNull(userDao,"articleImageDao cannot be null");
		this.userDao = userDao;
	} 
	
	public void setPasswordEncoder(final PasswordEncoder passwordEncoder){
		Validate.notNull(passwordEncoder,"passwordEncoder cannot be null");
		this.passwordEncoder = passwordEncoder;
	}
	
	public void setSiteDao(final SiteDAO siteDao){
		Validate.notNull(siteDao,"articleImageDao cannot be null");
		this.siteDao = siteDao;
	}

	@Override
	public void delete(UserDto dtoToDelete) {
		Validate.notNull(dtoToDelete ,"dtoToDelete cannot be null");
		// TODO Auto-generated method stub
		
	}
}
