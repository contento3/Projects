package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.AddRoleClickListener;
import com.contento3.web.user.listner.AddUserClickListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
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
		HorizontalLayout horizon = new HorizontalLayout();
		VerticalLayout verticl = new VerticalLayout();
		this.verticalLayout.addComponent(horizon);
		horizon.addComponent(verticl);
		verticl.addComponent(groupHeading);
		verticl.addComponent(new HorizontalRuler());
		verticl.setMargin(true);
		horizon.setWidth(100, Unit.PERCENTAGE);
		try
		{
		addUserButton(horizon);
		}
		catch(AuthorizationException ex){}
		
		horizon.setExpandRatio(verticl, 9);
	}

	/**
	 * display "Add User" button on the top of tab sheet
	 */
	private void addUserButton(HorizontalLayout horizontl){
		//final Button addButton = new Button("Add User", new UserPopup(contextHelper,userTable));
		//this.verticalLayout.addComponent(addButton);
		
		GridLayout toolbarGridLayout = new GridLayout(1,1);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddUserClickListener(contextHelper, userTable ));
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"page",listeners);
		builder.build();
		horizontl.addComponent(toolbarGridLayout);
		horizontl.setExpandRatio(toolbarGridLayout, 1);
	}

	/**
	 * Render group table to screen
	 * @param verticl 
	 */
	private void renderUserTable(VerticalLayout verticl) {
		final AbstractTableBuilder tableBuilder = new UserTableBuilder(contextHelper,userTable);
		try
		{
		tableBuilder.build((Collection)userService.findUsersByAccountId((Integer)SessionHelper.loadAttribute("accountId")));
		}catch(AuthorizationException ex){}
		verticl.addComponent(userTable);
	}

}
