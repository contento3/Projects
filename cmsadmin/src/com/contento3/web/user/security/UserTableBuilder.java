package com.contento3.web.user.security;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.common.dto.Dto;
import com.contento3.security.user.dto.SaltedHibernateUserDto;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.UserDeleteClickListener;
import com.vaadin.data.Container;
import com.vaadin.data.Item;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.BaseTheme;

public class UserTableBuilder extends AbstractTableBuilder  {
	/**
	 * Helper to get the spring bean
	 */
	final SpringContextHelper contextHelper;
	
	
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
	public UserTableBuilder(final SpringContextHelper helper,final Table table){
		super(table);
		this.contextHelper = helper;
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
		try
		{
		SaltedHibernateUserDto user = (SaltedHibernateUserDto) dto;
		Item item = userContainer.addItem(user.getName());
		item.getItemProperty("users").setValue(user.getName());
	
		//adding edit button item into list
	    final Button editLink = new Button("Edit users");
	    editLink.addClickListener(new UserPopup(contextHelper, userTable));
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
		deleteLink.addClickListener(new UserDeleteClickListener(user, userService, deleteLink, userTable));
		}catch(AuthorizationException ex){}
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

		userTable.setWidth(100, Unit.PERCENTAGE);
		userTable.setContainerDataSource(userContainer);
	}

}
