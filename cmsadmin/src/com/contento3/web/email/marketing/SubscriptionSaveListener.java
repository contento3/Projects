package com.contento3.web.email.marketing;

import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.service.SubscriptionService;
import com.contento3.account.service.AccountService;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;

public class SubscriptionSaveListener implements ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	/**
	 * Subscription service for article related operations
	 */
	private SubscriptionService subscriptionService;
	
	private AccountService accountService;
	
	private Integer subscriptionId;
	
	private Integer accountId;
	
	final SpringContextHelper helper;
	
	final Table table;
	
	final UIManagerContext uiContext;

	public SubscriptionSaveListener(final UIManagerContext uiContext){
		this.uiContext = uiContext; 
		this.helper = uiContext.getHelper();
		this.table = uiContext	.getListingTable();
		this.subscriptionService = (SubscriptionService)helper.getBean("subscriptionService");
		this.accountService = (AccountService)helper.getBean("accountService");
	}
	
	@Override
	public void click(final ClickEvent event) {
		final SubscriptionForm subscriptionForm = (SubscriptionForm)uiContext.getForm();
		SubscriptionDto subscriptionDto = null;
		subscriptionId = uiContext.getIdToEdit();
	
		if (null!= subscriptionId){
			subscriptionDto = subscriptionService.findById(subscriptionId);
		}
		else {
			subscriptionDto = new SubscriptionDto();
		}
			
		subscriptionDto.setSubscriptionName(subscriptionForm.getSubscriptionName().getValue());
		subscriptionDto.setDescription(subscriptionForm.getSubscriptionDescription().getValue());
		subscriptionDto.setAccount(accountService.findAccountById((Integer)SessionHelper.loadAttribute("accountId")));	
		subscriptionDto.setPlatformUser(((SaltedHibernateUserDto)SessionHelper.loadAttribute("loggedInUser")));
		
		if (null!= subscriptionId){
			subscriptionService.update(subscriptionDto);
			Notification.show("Subscription", "Subscription updated successfully", Type.TRAY_NOTIFICATION);
		}
		else {
			try {
				subscriptionService.create(subscriptionDto);
				Notification.show("Subscription", "Subscription saved successfully", Type.TRAY_NOTIFICATION);
			} catch (final EntityAlreadyFoundException e) {
				
			} catch (final EntityNotCreatedException e) {
				
			}
		}
	}

}