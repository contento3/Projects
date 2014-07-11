package com.contento3.web.email.marketing;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.util.CollectionUtils;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.service.SubscriptionService;
import com.contento3.common.dto.Dto;
import com.contento3.common.exception.EntityNotFoundException;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.EntityListener;
import com.contento3.web.common.helper.GenricEntityPicker;
import com.contento3.web.common.helper.SessionHelper;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

public class SubscriptionListPicker extends EntityListener implements ClickListener,com.vaadin.event.MouseEvents.ClickListener {
	
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = Logger.getLogger(SubscriptionListPicker.class);

	private final VerticalLayout mainLayout;
	
	private SubscriptionService subscriptionService;

	final UIManagerContext context;
	
	final Integer newsletterId;
	
	final NewsletterService newsletterService;
	
	Collection<Dto> assignedDtos;
	
	/**
	 * 
	 * @param context
	 */
	public SubscriptionListPicker(final UIManagerContext context,final Integer newsletterId){
		subscriptionService = (SubscriptionService)context.getHelper().getBean("subscriptionService");
		mainLayout = new VerticalLayout();
		this.context = context;
		this.newsletterId = newsletterId;
		this.newsletterService = (NewsletterService)context.getHelper().getBean("newsletterService");
	}
	
	/**
	 * Button Click Event
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void buttonClick(ClickEvent event) {
		openPicker();	
	}
	
	private void openPicker(){
		try
		{
			final Collection<String> listOfColumns = new ArrayList<String>();
			listOfColumns.add("Subscription List");
			
			final Collection<Dto> dtos = (Collection) subscriptionService.findByAccountId((Integer)SessionHelper.loadAttribute("accountId"));
			assignedDtos = (Collection) newsletterService.findById(newsletterId).getSubscriptionList();

			final GenricEntityPicker subscriptionPicker = new GenricEntityPicker(dtos,assignedDtos,listOfColumns,mainLayout,this,false);
			subscriptionPicker.build();
		}
		catch(final AuthorizationException ex) {
			Notification.show("You are not permitted to assign category to articles");
		} 
		catch (final EntityNotFoundException e) {
			LOGGER.debug(e.getMessage());
		} 
	}
	
	/**
	 * Assign selected category to article
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateList() {
		/* update newsletter */
		
		Collection<String> selectedItems =(Collection<String>) this.mainLayout.getData();
		if(selectedItems != null){
			final NewsletterService newsletterService = (NewsletterService) context.getHelper().getBean("newsletterService");
			final NewsletterDto newsletter = newsletterService.findById(newsletterId);
			newsletter.getSubscriptionList().clear();
			for(String name : selectedItems ){
				Integer subscriptionId = Integer.parseInt(name);
				SubscriptionDto subscriptionDto = subscriptionService.findById(subscriptionId);
				boolean isAddable = true;
				for (Dto assignedDto : assignedDtos){
					if (assignedDto.getId()==subscriptionDto.getId()){
						isAddable = false;
					}
				}
				if (isAddable){
					newsletter.getSubscriptionList().add(subscriptionDto);
				}
			}//end outer for
			
			newsletterService.update(newsletter);
			Notification.show("Assigned"," successfully assigned to "+newsletter.getName(),Notification.Type.HUMANIZED_MESSAGE);
			
		}
		
	}

	@Override
	public void click(com.vaadin.event.MouseEvents.ClickEvent event) {
		openPicker();
	}





}
