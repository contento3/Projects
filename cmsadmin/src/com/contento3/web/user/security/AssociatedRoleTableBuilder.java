package com.contento3.web.user.security;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.common.dto.Dto;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.RoleDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class AssociatedRoleTableBuilder extends AbstractTableBuilder {

	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	
	/**
	 * user service used for user related operations
	 */
	final RoleService roleService;
	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param table
	 */
	public AssociatedRoleTableBuilder(final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
		this.roleService = (RoleService) contextHelper.getBean("roleService");
	}
	
	/**
	 * Assign item to user table
	 * @param dto
	 * @param userTable
	 * @param userContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table roleTable,final Container roleContainer) {
		try
		{
		RoleDto role = (RoleDto) dto;
		Item item = roleContainer.addItem(role.getName());
		item.getItemProperty("roles").setValue(role.getName());
	
		//adding edit button item into list
//	    final Button editLink = new Button("Add");
//	    editLink.addClickListener(new RolePopup(contextHelper, roleTable));
//		editLink.setCaption("Edit");
//		editLink.setData(role.getName());
//		editLink.addStyleName("edit");
//		editLink.setStyleName(BaseTheme.BUTTON_LINK);
//		item.getItemProperty("edit").setValue(editLink);
		
		//adding delete button item  into list
//		final Button deleteLink = new Button();
//		deleteLink.setCaption("Delete");
//		deleteLink.setData((role.getName()));
//		deleteLink.addStyleName("delete");
//		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
//		item.getItemProperty("delete").setValue(deleteLink);
//		deleteLink.addClickListener(new RoleDeleteClickListener(role, roleService, deleteLink, roleTable));
		}catch(AuthorizationException ex){}
	}

	/**
	 * Create header for table
	 * @param userTable
	 * @param userContainer
	 */
	@Override
	public void buildHeader(final Table roleTable,final Container userContainer) {
		userContainer.addContainerProperty("roles", String.class, null);
//		userContainer.addContainerProperty("edit", Button.class, null);
//		userContainer.addContainerProperty("delete", Button.class, null);

		roleTable.setWidth(100, Unit.PERCENTAGE);
		roleTable.setContainerDataSource(userContainer);
	}

	/**
	 * Create empty table
	 * @param userContainer
	 */
	@Override
	public void buildEmptyTable(final Container container){
		final Item item = container.addItem("-1");
		item.getItemProperty("roles").setValue("No record found");
	}


}
