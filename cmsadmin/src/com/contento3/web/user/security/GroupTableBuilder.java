package com.contento3.web.user.security;

import com.contento3.common.dto.Dto;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.GroupDeleteClickListener;
import com.vaadin.data.Item;
import com.vaadin.data.util.IndexedContainer;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

/**
 * Implementation of TableBuilder for 
 * SiteDomains table
 * @author HAMMAD
 *
 */
public class GroupTableBuilder extends AbstractTableBuilder {
	
	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	 /**
     * Represents the parent window of the template ui
     */
	final Window window;
	
	/**
	 * Group service used for group related operations
	 */
	final GroupService groupService;
	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param table
	 */
	public GroupTableBuilder(final Window window,final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
		this.window = window;
		this.groupService = (GroupService) contextHelper.getBean("groupService");
	}
	
	/**
	 * Assign item to group table
	 * @param dto
	 * @param groupTable
	 * @param groupContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table groupTable,final IndexedContainer groupContainer) {
		GroupDto group = (GroupDto) dto;
		Item item = groupContainer.addItem(group.getGroupId());
		item.getItemProperty("groups").setValue(group.getGroupName());
		//adding edit button item into list
		final Button editLink = new Button("Edit groups",new GroupPopup(window, contextHelper, groupTable), "openButtonClick");
		editLink.setCaption("Edit");
		editLink.setData(group.getGroupId());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("edit").setValue(editLink);
		
		//adding delete button item  into list
		final Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData((group.getGroupId()));
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addListener(new GroupDeleteClickListener(group, contextHelper, deleteLink, groupTable));
		
	}

	/**
	 * Create header for table
	 * @param groupTable
	 * @param groupContainer
	 */
	@Override
	public void buildHeader(final Table groupTable,final IndexedContainer groupContainer) {
		groupContainer.addContainerProperty("groups", String.class, null);
		groupContainer.addContainerProperty("edit", Button.class, null);
		groupContainer.addContainerProperty("delete", Button.class, null);

		groupTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		groupTable.setContainerDataSource(groupContainer);
	}

	/**
	 * Create empty table
	 * @param groupContainer
	 */
	@Override
	public void buildEmptyTable(final IndexedContainer groupContainer){
		final Item item = groupContainer.addItem("-1");
		item.getItemProperty("groups").setValue("No record found.");
	}
		
}
