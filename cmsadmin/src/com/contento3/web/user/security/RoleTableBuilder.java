package com.contento3.web.user.security;

import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.common.dto.Dto;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.service.RoleService;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.RoleDeleteClickListener;
import com.contento3.web.user.listner.UserDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.themes.BaseTheme;

public class RoleTableBuilder extends AbstractTableBuilder {
	final SpringContextHelper contextHelper;
	final Window window;
	final RoleService roleService;



	public RoleTableBuilder(final Window window,final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
		this.window = window;
		this.roleService = (RoleService) contextHelper.getBean("roleService");
	}
	@Override
	public void assignDataToTable(Dto dto, Table roletable, Container rolecontainer) {
		// TODO Auto-generated method stub
		RoleDto role = (RoleDto) dto;
		Item item = rolecontainer.addItem(role.getName());
		item.getItemProperty("role").setValue(role.getName());
	
		//adding edit button item into list
	    final Button editLink = new Button("Edit roles",new RolePopup(window, contextHelper, roletable), "openButtonClick");
		editLink.setCaption("Edit");
		editLink.setData(role.getRoleid());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("edit").setValue(editLink);
		
		
		
		//adding delete button item  into list
		final Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData((role.getName()));
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addListener(new RoleDeleteClickListener(role, roleService, window, deleteLink, roletable));
		
		
	}

	@Override
	public void buildHeader(Table roletable, Container rolecontainer) {
		// TODO Auto-generated method stub
		rolecontainer.addContainerProperty("role", String.class, null);
		rolecontainer.addContainerProperty("edit", Button.class, null);
		rolecontainer.addContainerProperty("delete", Button.class, null);

		roletable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		roletable.setContainerDataSource(rolecontainer);
		
	}

	/**
	 * Create empty table
	 */
	@Override
	public void buildEmptyTable(final Container roleContainer) {
		Item item = roleContainer.addItem("-1");
		item.getItemProperty("role").setValue("No record found.");
	}

}
