package com.contento3.web.user.security;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.common.dto.Dto;
import com.contento3.security.group.dto.GroupDto;
import com.contento3.security.group.service.GroupService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.GroupDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

/**
 * Implementation of TableBuilder for 
 * Group table
 * @author HAMMAD
 *
 */
public class GroupTableBuilder extends AbstractTableBuilder {
	
	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
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
	public GroupTableBuilder(final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
		this.groupService = (GroupService) contextHelper.getBean("groupService");
	}
	
	/**
	 * Assign item to group table
	 * @param dto
	 * @param groupTable
	 * @param groupContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table groupTable,final Container groupContainer) {
		try
		{
		GroupDto group = (GroupDto) dto;
		Item item = groupContainer.addItem(group.getGroupId());
		item.getItemProperty("groups").setValue(group.getGroupName());
		//adding edit button item into list
		final Button editLink = new Button("Edit groups",new GroupPopup(contextHelper, groupTable));
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
		deleteLink.addClickListener(new GroupDeleteClickListener(group, groupService, deleteLink, groupTable));
		
		//add view button item into list
		final Button viewLink = new Button("View users",new AssociatedUserPopup(contextHelper, new Table()));
		viewLink.setCaption("View");
		viewLink.setData(group.getGroupId());
		viewLink.addStyleName("associated users");
		viewLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("associated users").setValue(viewLink);
		
		/*
		final Button viewRoleLink = new Button("View Roles",new AssociatedRolePopup(contextHelper, new Table()));
		viewLink.setCaption("ViewRoles");
		viewLink.setData(group.getRoles());
		viewLink.addStyleName("associated roles");
		viewLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("associated roles").setValue(viewLink);
		*/
		}catch(AuthorizationException ex){}
		
		
	}

	/**
	 * Create header for table
	 * @param groupTable
	 * @param groupContainer
	 */
	@Override
	public void buildHeader(final Table groupTable,final Container groupContainer) {
		groupContainer.addContainerProperty("groups", String.class, null);
		groupContainer.addContainerProperty("edit", Button.class, null);
		groupContainer.addContainerProperty("delete", Button.class, null);
		groupContainer.addContainerProperty("associated users", Button.class, null);
		//groupContainer.addContainerProperty("associated roles", Button.class, null);
		groupTable.setWidth(100, Unit.PERCENTAGE);
		groupTable.setContainerDataSource(groupContainer);
	}

	/**
	 * Create empty table
	 * @param groupContainer
	 */
	@Override
	public void buildEmptyTable(final Container groupContainer){
		final Item item = groupContainer.addItem("-1");
		item.getItemProperty("groups").setValue("No record found.");
	}
		
}
