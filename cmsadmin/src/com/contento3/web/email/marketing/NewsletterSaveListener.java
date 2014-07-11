package com.contento3.web.email.marketing;

import java.util.Collection;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.contento3.account.service.AccountService;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.event.MouseEvents.ClickEvent;
import com.vaadin.event.MouseEvents.ClickListener;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;

public class NewsletterSaveListener implements ClickListener {
		
	private static final long serialVersionUID = 1L;

	/**
	 * Newsletter service for article related operations
	 */
	private NewsletterService newsletterService;

	private AccountService accountService;
	
	private Integer newsletterId;

	private Integer accountId;

	final SpringContextHelper helper;

	final Table table;

	final UIManagerContext uiContext;
	
	public NewsletterSaveListener(final UIManagerContext uiContext){
		this.uiContext = uiContext; 
		this.helper = uiContext.getHelper();
		this.table = uiContext.getListingTable();
		this.newsletterService = (NewsletterService)helper.getBean("newsletterService");
		this.accountService = (AccountService)helper.getBean("accountService");
	}
	
	@Override
	public void click(final ClickEvent event) {
		final NewsletterForm newsletterForm = (NewsletterForm)uiContext.getForm();
		NewsletterDto newsletterDto = null;
		newsletterId = uiContext.getIdToEdit();

		if (null!= newsletterId){
			newsletterDto = newsletterService.findById(newsletterId);
		}
		else {
			newsletterDto = new NewsletterDto();
		}
			
		newsletterDto.setNewsletterName(newsletterForm.getNewsletterName().getValue());
		newsletterDto.setDescription(newsletterForm.getDescription().getValue());
		newsletterDto.setStatus("SAVED");
		newsletterDto.setSubject(newsletterForm.getSubject().getValue());
		newsletterDto.setAccount(accountService.findAccountById((Integer)SessionHelper.loadAttribute("accountId")));	
		
    	final TemplateDto template = (TemplateDto)UI.getCurrent().getData();
    	
    	if (template==null && newsletterDto.getTemplate()==null){
    		Notification.show("Newsletter", "Please select template for newsletter", Type.TRAY_NOTIFICATION);
    	}
    	else if (template!=null) {
    		newsletterDto.setTemplate(template);
    	}

		if (null!= newsletterId){
			newsletterService.update(newsletterDto);
			Notification.show("Newsletter", "Newsletter saved successfully", Type.TRAY_NOTIFICATION);
		}		
		else {
			try {
		    	newsletterService.create(newsletterDto);
				Notification.show("Newsletter", "Newsletter saved successfully", Type.TRAY_NOTIFICATION);
			} catch (final EntityAlreadyFoundException e) {
				Notification.show("Newsletter", "Newsletter with same name already exist.", Type.TRAY_NOTIFICATION);
			} catch (final EntityNotCreatedException e) {
				Notification.show("Newsletter", "Unable to create newsletter", Type.TRAY_NOTIFICATION);
			}
		}
		
		uiContext.setIdToEdit(null);
	}
		
	/**
	 * Reset table
	 */
	 @SuppressWarnings("rawtypes")
	 private void resetTable(){
		final AbstractTableBuilder tableBuilder = new NewsletterTableBuilder(uiContext);
		final Collection<NewsletterDto> newsletters=newsletterService.findByAccountId(accountId);
		tableBuilder.rebuild((Collection)newsletters);
	}
}
