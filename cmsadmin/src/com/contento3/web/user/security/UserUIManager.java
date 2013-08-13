package com.contento3.web.user.security;

import java.util.Collection;

import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class UserUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper contextHelper;
    
    /**
     * Represents the parent window of the template ui
     */
	//private Window parentWindow;
	
	/**
	 * layout for group manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	/**
	 * TabSheet serves as the parent container for the group manager
	 */
	private TabSheet uiTabSheet;
	
	/**
	 * Table contain group items
	 */
	Table userTable = new Table();

	/**
	 * UserService
	 */
	SaltedHibernateUserService userService;
	
	/**
	 * Constructor
	 * @param helper
	 * @param parentWindow
	 */
	public UserUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.contextHelper = helper;
		this.uiTabSheet = uiTabSheet;
		this.userService = (SaltedHibernateUserService) this.contextHelper.getBean("saltedHibernateUserService");
	}

	@Override
	public void render() {
	
	}

	@Override
	public Component render(String command) {
		this.uiTabSheet.setHeight(100, Unit.PERCENTAGE);
		Tab userTab = uiTabSheet.addTab(verticalLayout, "User Management",new ExternalResource("images/security.png"));
		userTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Unit.PERCENTAGE);

		renderUserListing();
		return uiTabSheet;
	}

	@Override
	public Component render(final String command, final Integer entityFilterId) {
		return null;
	}

	@Override
	public Component render(final String command,
			final HierarchicalContainer treeItemContainer) {
		return null;
	}

	/**
	 * Renders user listing 
	 */
	private void renderUserListing() {
		Label groupHeading = new Label("User Manager");
		groupHeading.setStyleName("screenHeading");
		this.verticalLayout.addComponent(groupHeading);
		this.verticalLayout.addComponent(new HorizontalRuler());
		this.verticalLayout.setMargin(true);
		addUserButton();
		renderUserTable();
	}

	/**
	 * display "Add User" button on the top of tab sheet
	 */
	private void addUserButton(){
		final Button addButton = new Button("Add User", new UserPopup(contextHelper,userTable));
		this.verticalLayout.addComponent(addButton);
	}

	/**
	 * Render group table to screen
	 */
	private void renderUserTable() {
		final AbstractTableBuilder tableBuilder = new UserTableBuilder(contextHelper,userTable);
		tableBuilder.build((Collection)userService.findUsersByAccountId((Integer)SessionHelper.loadAttribute("accountId")));
		this.verticalLayout.addComponent(userTable);
	}

}
