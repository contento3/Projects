package com.contento3.web.email.marketing;

import org.apache.commons.lang3.StringUtils;

import com.c3.email.marketing.subscription.dto.SubscriberDto;
import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.service.SubscriptionService;
import com.contento3.web.UIManagerContext;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

public class SubscriberAddListener implements ClickListener {

	private static final long serialVersionUID = 1L;

	final UIManagerContext context; 
	
	final SubscriptionService subscriptionService; 
	
	final SubscriptionDto subscriptionDto;
	
	public SubscriberAddListener (final UIManagerContext context,final SubscriptionDto subscriptionDto){
		this.context = context;
		this.subscriptionService = (SubscriptionService)context.getHelper().getBean("subscriptionService");
		this.subscriptionDto = subscriptionDto;
	}
	
	@Override
	public void buttonClick(final ClickEvent event) {
		final SubscriptionForm form = (SubscriptionForm) context.getForm();
		final SubscriberDto subscriber = new SubscriberDto();
	
		if (!StringUtils.isEmpty(form.getSubscriberEmail().getValue()) && !StringUtils.isEmpty(form.getSubscriberFirstName().getValue()) && !StringUtils.isEmpty(form.getSubscriberLastName().getValue())){
			subscriber.setEmail(form.getSubscriberEmail().getValue());
			subscriber.setFirstName(form.getSubscriberFirstName().getValue());
			subscriber.setLastName(form.getSubscriberLastName().getValue());
			subscriptionDto.getSubscribers().add(subscriber);
			subscriptionService.update(subscriptionDto);
			
			Notification.show("Add Subscriber","Subscriber added successfully",Notification.Type.TRAY_NOTIFICATION);
			form.getSubscriberEmail().setValue("");
			form.getSubscriberFirstName().setValue("");
			form.getSubscriberLastName().setValue("");
		}
		else {
			Notification.show("Add Subscriber failed","Please fill all fields to add subscriber",Notification.Type.TRAY_NOTIFICATION);
		}
		
	}

}
