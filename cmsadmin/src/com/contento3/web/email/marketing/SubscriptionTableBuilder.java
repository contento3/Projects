package com.contento3.web.email.marketing;

import com.c3.email.marketing.subscription.dto.SubscriptionDto;
import com.c3.email.marketing.subscription.service.SubscriptionService;
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

public class SubscriptionTableBuilder extends AbstractTableBuilder {

	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;

	final UIManagerContext uiContext;
	
	/**
	 * Article service used for article related operations
	 */
	final SubscriptionService subscriptionService;

	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param table
	 */
	public SubscriptionTableBuilder(final UIManagerContext uiContext) {
		super(uiContext.getListingTable());
		this.uiContext = uiContext;
		this.contextHelper = uiContext.getHelper();
		this.subscriptionService = (SubscriptionService) contextHelper.getBean("subscriptionService");
	}    
     
	/**
	 * Assign data to table
	 * @param dto
	 * @param articleTable
	 * @param articleContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table newsletterTable,final Container subscriptionContainer) {
		final SubscriptionDto subscriptionDto = (SubscriptionDto) dto;
		Item item = subscriptionContainer.addItem(subscriptionDto.getId());
		item.getItemProperty("subscription").setValue(subscriptionDto.getName());

		Button editLink = new Button();
		editLink.setCaption("Edit");
		editLink.setData(subscriptionDto.getId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		ClickListener subscriptionListener = new SubscriptionAddEditListener(this.uiContext);
		editLink.addClickListener(subscriptionListener);
		item.getItemProperty("edit").setValue(editLink);
		
		Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData(subscriptionDto.getId());
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addClickListener(new EntityDeleteClickListener(subscriptionDto, subscriptionService, deleteLink, newsletterTable,"Are you sure you want to delete subscription"));
	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table  table,final Container container) {
		container.addContainerProperty("subscription", String.class, null);
		table.setColumnExpandRatio("subscription", 30);
		container.addContainerProperty("edit", Button.class, null);
		container.addContainerProperty("delete", Button.class, null);
		table.setWidth(100, Unit.PERCENTAGE);
		table.setContainerDataSource(container);
	}

	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final Container container) {
		Item item = container.addItem("-1");
		item.getItemProperty("subscription").setValue("No record found.");
	}

}
