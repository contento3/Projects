package com.contento3.web.user.security;

import com.contento3.common.dto.Dto;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.UserDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;

public class UserTableBuilder extends AbstractTableBuilder  {
	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	 /**
     * Represents the parent window of the template ui
     */
	final Window window;
	
	/**
	 * user service used for user related operations
	 */
	final SaltedHibernateUserService userService;
	/**
	 * Constructor
	 * @param window
	 * @param helper
	 * @param table
	 */
	public UserTableBuilder(final Window window,final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
		this.window = window;
		this.userService = (SaltedHibernateUserService) contextHelper.getBean("saltedHibernateUserService");
	}
	
	/**
	 * Assign item to user table
	 * @param dto
	 * @param userTable
	 * @param userContainer
	 */
	@Override
	public void assignDataToTable(final Dto dto,final Table userTable,final Container userContainer) {
		SaltedHibernateUserDto user = (SaltedHibernateUserDto) dto;
		Item item = userContainer.addItem(user.getName());
		item.getItemProperty("users").setValue(user.getName());
	
		//adding edit button item into list
	    final Button editLink = new Button("Edit users",new UserPopup(window, contextHelper, userTable), "openButtonClick");
		editLink.setCaption("Edit");
		editLink.setData(user.getName());
		editLink.addStyleName("edit");
		editLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("edit").setValue(editLink);
		
		//adding delete button item  into list
		final Button deleteLink = new Button();
		deleteLink.setCaption("Delete");
		deleteLink.setData((user.getName()));
		deleteLink.addStyleName("delete");
		deleteLink.setStyleName(BaseTheme.BUTTON_LINK);
		item.getItemProperty("delete").setValue(deleteLink);
		deleteLink.addListener(new UserDeleteClickListener(user, userService, window, deleteLink, userTable));
		
	}

	/**
	 * Create header for table
	 * @param userTable
	 * @param userContainer
	 */
	@Override
	public void buildHeader(final Table userTable,final Container userContainer) {
		userContainer.addContainerProperty("users", String.class, null);
		userContainer.addContainerProperty("edit", Button.class, null);
		userContainer.addContainerProperty("delete", Button.class, null);

		userTable.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		userTable.setContainerDataSource(userContainer);
	}

}
