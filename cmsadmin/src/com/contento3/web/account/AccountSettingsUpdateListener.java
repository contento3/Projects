package com.contento3.web.account;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

import com.contento3.account.service.AccountService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.terminal.gwt.server.WebApplicationContext;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Window;

/**
 * Listener that updates the currently logged in user information.
 * @author hammad.afridi
 *
 */
public class AccountSettingsUpdateListener implements Button.ClickListener {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Bean that represent the user account form
	 */
	AccountForm accountForm;
	
	/**
	 * Service layer for account entity
	 */
	AccountService accountService;
	
	/**
	 * Service layer for user 
	 */
	SaltedHibernateUserService userService;
	
	/**
	 * Parent window
	 */
	Window parentWindow;
	
	/**
	 * Helper that loads the spring bean.
	 */
	SpringContextHelper contextHelper;
	
	public AccountSettingsUpdateListener(final Window parentWindow, 
										final SaltedHibernateUserService userService, 
										final AccountService accountService, 
										final AccountForm accountForm, 
										final SpringContextHelper contextHelper) {	
		this.parentWindow = parentWindow;
		this.userService = userService;
		this.accountForm = accountForm;
		this.contextHelper = contextHelper;
		this.accountService = accountService;
		
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		final String newPswd = accountForm.getNewPassword().getValue().toString();
		final String confirmPswd = accountForm.getConfirmNewPassword().getValue().toString();
		
		if(!newPswd.equals(confirmPswd)){
			parentWindow.showNotification("Passwords do not match.");
			return;
		}

		final WebApplicationContext webContext = (WebApplicationContext) parentWindow.getApplication().getContext();
		final String userName = (String) webContext.getHttpSession().getAttribute("userName");
		final SaltedHibernateUserDto userDto = userService.findUserByUsername(userName);
		userDto.setEmail(accountForm.getEmail().getValue().toString());
		userDto.setFirstName(accountForm.getFirstName().getValue().toString());
		userDto.setLastName(accountForm.getLastName().getValue().toString());
		
		if (StringUtils.isNotEmpty(newPswd)){
			final Hash hashedPswd = new Sha256Hash(newPswd, userDto.getSalt(), 1);
			userDto.setPassword(hashedPswd.toString());
		}
				
		userService.update(userDto);
		parentWindow.showNotification(userDto.getFirstName() + ", you have changed your information successfully.");
		parentWindow.removeWindow(event.getComponent().getWindow());
	}

}
