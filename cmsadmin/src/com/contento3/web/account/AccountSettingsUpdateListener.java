package com.contento3.web.account;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
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

public class AccountSettingsUpdateListener implements Button.ClickListener {
	private static final long serialVersionUID = 1L;
	
	AccountForm accountForm;
	AccountService accountService;
	SaltedHibernateUserService userService;
	Window parentWindow;
	SpringContextHelper contextHelper;
	Integer accountId;
	String userName;
	
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
		
		WebApplicationContext webContext = (WebApplicationContext) parentWindow.getApplication().getContext();
		this.accountId = (Integer) webContext.getHttpSession().getAttribute("accountId");
		this.userName = (String) webContext.getHttpSession().getAttribute("userName");
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		final String newPswd = accountForm.getNewPassword().getValue().toString();
		final String confirmPswd = accountForm.getConfirmNewPassword().getValue().toString();
		
		if(!newPswd.equals(confirmPswd)){
			parentWindow.showNotification("Passwords do not match.");
			return;
		}
		
		String salt = new SecureRandomNumberGenerator().nextBytes().toBase64();
		
		SaltedHibernateUserDto userDto = new SaltedHibernateUserDto();
		Hash hashedPswd = new Sha256Hash(newPswd, salt, 1);
		
		userDto.setAccount( accountService.findAccountById(accountId) );
		userDto.setEnabled(true);
		userDto.setPassword(hashedPswd.toString());
		userDto.setUserName(userName);
		userDto.setSalt(salt);
		
		userService.update(userDto);
		parentWindow.showNotification("Password changed successfully.");
		parentWindow.removeWindow(event.getComponent().getWindow());
	}

}
