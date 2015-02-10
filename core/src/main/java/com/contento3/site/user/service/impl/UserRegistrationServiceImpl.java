package com.contento3.site.user.service.impl;

import org.apache.commons.lang.Validate;

import com.contento3.cms.site.structure.dao.SiteDAO;
import com.contento3.cms.site.structure.model.Site;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.security.PasswordInfo;
import com.contento3.common.security.PasswordUtility;
import com.contento3.module.email.EmailService;
import com.contento3.module.email.model.EmailInfo;
import com.contento3.security.user.dao.SaltedHibernateUserDao;
import com.contento3.security.user.model.SaltedHibernateUser;
import com.contento3.site.user.dao.UserDao;
import com.contento3.site.user.dto.UserDto;
import com.contento3.site.user.model.User;
import com.contento3.site.user.service.UserRegistrationAssembler;
import com.contento3.site.user.service.UserRegistrationService;

public class UserRegistrationServiceImpl implements UserRegistrationService {

	private UserDao userDao;

	private SaltedHibernateUserDao platformUserDao;
	
	private SiteDAO siteDao;

	private EmailService emailService;

	private UserRegistrationAssembler userRegistrationAssembler;
	
	@Override
	public Integer create(UserDto dto) throws EntityAlreadyFoundException {
//		Validate.notNull(dto,"dto cannot be null");
//		
//		String password = dto.getPassword();
//			
//		User user = new User();
//		user.setPassword(hashedPassword);
//		user.setUsername(dto.getUsername());
//		user.setPasswordReminder(dto.getPasswordReminder());
//		user.setEnabled(true);
//		
//		return userDao.persist(user);
		return null;
	}
	
	public void create(final UserDto userDto,final String siteDomain) throws EntityAlreadyFoundException {
		Validate.notNull(userDto,"articleImageDao cannot be null");
		Validate.notNull(siteDomain,"articleImageDao cannot be null");

		final Site site = siteDao.findByDomain(siteDomain, false);
		final User user = userDao.findByUsernameAndSiteId(userDto.getUsername(),site.getSiteId());
		
		if (user!=null){
			throw new EntityAlreadyFoundException(String.format("User with username %s",userDto.getUsername()));
		}
		else {
			final PasswordInfo info = PasswordUtility.createSaltedPassword(userDto.getPassword());
	
			//Create contento3 account,only executes when the account id = 1 and site is contento3.com
			if (siteDomain.equals("www.contento3.com") && site.getAccount().getAccountId()==1){
				final SaltedHibernateUser platformUser = new SaltedHibernateUser();
				platformUser.setAccount(site.getAccount());
				platformUser.setEmail(userDto.getUsername());
				platformUser.setEnabled(true);
				platformUser.setPassword(info.getPassword());
				platformUser.setSalt(info.getSalt());
				platformUser.setFirstName(userDto.getFirstName());
				platformUser.setLastName(userDto.getLastName());
				platformUserDao.persist(platformUser);
				sendEmail(platformUser.getEmail());
			}
			else {
				final User newUser = new User();
				userRegistrationAssembler.dtoToDomain(userDto,newUser);
				newUser.setPassword(info.getPassword());
				newUser.setSalt(info.getSalt());
				newUser.setSite(site);
				userDao.persist(newUser);
	
				//Send confirmation/welcome email
				sendEmail(newUser.getUsername());
			}
		}
	}

	//TODO this needs to be implemented properly.
	private void sendEmail(final String to){
		final EmailInfo emailInfo = new EmailInfo();
		emailInfo.setFrom("info@contento3.com");
		emailInfo.setTo(to);
		//emailInfo.setSubject(reason);
		emailInfo.setEmailText("testing text");
		emailService.send(emailInfo);
	}
	
	public void setUserDao(final UserDao userDao){
		Validate.notNull(userDao,"userDao cannot be null");
		this.userDao = userDao;
	} 

	public void setPlatformUserDao(final SaltedHibernateUserDao platformUserDao){
		Validate.notNull(platformUserDao,"platformUserDao cannot be null");
		this.platformUserDao = platformUserDao;
	} 

	public void setSiteDao(final SiteDAO siteDao){
		Validate.notNull(siteDao,"siteDao cannot be null");
		this.siteDao = siteDao;
	}

	public void setEmailService(final EmailService emailService){
		this.emailService = emailService;
	}
	
	public void setUserRegistrationAssembler(final UserRegistrationAssembler userRegistrationAssembler){
		this.userRegistrationAssembler = userRegistrationAssembler;
	}
	
	@Override
	public void delete(UserDto dtoToDelete) {
		Validate.notNull(dtoToDelete ,"dtoToDelete cannot be null");
	}
}
