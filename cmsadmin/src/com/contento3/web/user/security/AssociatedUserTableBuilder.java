package com.contento3.web.user.security;

import com.contento3.common.dto.Dto;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;

/**
 * Implementation of TableBuilder for 
 * Associated user table
 */
public class AssociatedUserTableBuilder  extends AbstractTableBuilder {

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
	public AssociatedUserTableBuilder(final Window window,final SpringContextHelper helper,final Table table) {
		super(table);
		this.contextHelper = helper;
		this.window = window;
	}

	/**
	 * Insert item into table
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table table,final Container container) {
		SaltedHibernateUserDto user= (SaltedHibernateUserDto) dto;
		Item item = container.addItem(user.getName());
		item.getItemProperty("users").setValue(user.getName());

	}

	/**
	 * Build header for table
	 */
	@Override
	public void buildHeader(final Table table,final Container container) {

		container.addContainerProperty("users", String.class, null);
		table.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		table.setContainerDataSource(container);
	}

	/**
	 * Build empty table
	 */
	@Override
	public void buildEmptyTable(final Container container) {
		final Item item = container.addItem("-1");
		item.getItemProperty("users").setValue("No record found.");	
	}

}
