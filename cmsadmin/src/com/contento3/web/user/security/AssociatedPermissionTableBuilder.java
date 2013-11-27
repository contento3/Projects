package com.contento3.web.user.security;

import com.contento3.common.dto.Dto;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.contento3.security.permission.dto.*;

public class AssociatedPermissionTableBuilder extends AbstractTableBuilder{
	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	 /**
     * Represents the parent window of the template ui
     */
	final Window window;
	
	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param table
	 */
	public AssociatedPermissionTableBuilder(final Window window,final SpringContextHelper helper,final Table table) {
		super(table);
		this.contextHelper = helper;
		this.window = window;
	}

	@Override
	public void assignDataToTable(Dto dto, Table table, Container container) {
		// TODO Auto-generated method stub
		PermissionDto permission= (PermissionDto) dto;
		Item item = container.addItem(permission.getName());
		item.getItemProperty("permissions").setValue(permission.getName());
	}

	@Override
	public void buildHeader(Table table, Container container) {
		// TODO Auto-generated method stub
		container.addContainerProperty("permissions", String.class, null);
		table.setWidth(100, Unit.PERCENTAGE);
		table.setContainerDataSource(container);
	}
	/**
	 * Build empty table
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void buildEmptyTable(final Container container) {
		final Item item = container.addItem("-1");
		item.getItemProperty("permissions").setValue("No record found.");	
	}

}
