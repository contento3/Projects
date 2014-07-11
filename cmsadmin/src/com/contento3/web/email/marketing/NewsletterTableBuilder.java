package com.contento3.web.email.marketing;

import com.c3.email.marketing.newsletter.dto.NewsletterDto;
import com.c3.email.marketing.newsletter.service.NewsletterService;
import com.contento3.common.dto.Dto;
import com.contento3.web.UIManagerContext;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.site.listener.EntityDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class NewsletterTableBuilder extends AbstractTableBuilder {


	final UIManagerContext uiContext;
	
	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;

	/**
	 * TabSheet serves as the parent container for the article manager
	 */
	private TabSheet tabSheet;

	/**
	 * Article service used for article related operations
	 */
	final NewsletterService newsletterService;

	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param table
	 */
	public NewsletterTableBuilder(final UIManagerContext uiContext) {
		super(uiContext.getListingTable());
		this.uiContext = uiContext;
		this.contextHelper = uiContext.getHelper();
		this.newsletterService = (NewsletterService) contextHelper.getBean("newsletterService");
	}    
     
	/**
	 * Assign data to table
	 * @param dto
	 * @param articleTable
	 * @param articleContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table newsletterTable,final Container newsletterContainer) {
		final NewsletterDto newsletterDto = (NewsletterDto) dto;
		Item item = newsletterContainer.addItem(newsletterDto.getId());
		item.getItemProperty("newsletters").setValue(newsletterDto.getName());

		Button editLink = new Button();
		editLink.setCaption("Edit");
		editLink.setData(newsletterDto.getId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		ClickListener newsletterListener = new NewsletterAddEditListener(uiContext);
		editLink.addClickListener(newsletterListener);
		item.getItemProperty("edit").setValue(editLink);
		
		Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(newsletterDto.getId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addClickListener(new EntityDeleteClickListener<NewsletterDto>(newsletterDto, newsletterService, deleteLink, newsletterTable,"Are you sure you want to delete newsletter?"));
	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table newsletterTable,final Container newsletterContainer) {
		newsletterContainer.addContainerProperty("newsletters", String.class, null);
		newsletterTable.setColumnExpandRatio("newsletters", 30);
		newsletterContainer.addContainerProperty("edit", Button.class, null);
		newsletterContainer.addContainerProperty("delete", Button.class, null);
		newsletterTable.setWidth(100, Unit.PERCENTAGE);
		newsletterTable.setContainerDataSource(newsletterContainer);
	}

	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final Container articleContainer) {
		Item item = articleContainer.addItem("-1");
		item.getItemProperty("newsletters").setValue("No record found.");
	}

}