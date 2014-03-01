package com.contento3.web.user.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.shiro.authz.AuthorizationException;

import com.contento3.security.role.service.RoleService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.ScreenToolbarBuilder;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.contento3.web.user.listner.AddRoleClickListener;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.Component;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.TabSheet.Tab;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;

public class RoleUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper contextHelper;
    
	
	/**
	 * layout for group manager screen
	 */
	private VerticalLayout verticalLayout = new VerticalLayout();

	/**
	 * TabSheet serves as the parent container for the group manager
	 */
	private TabSheet uiTabSheet;
	private TabSheet TabSheet;
	/**
	 * Table contain group items
	 */
	Table roleTable = new Table();

	/**
	 * UserService
	 */
	RoleService roleService;
	
	public RoleUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper) {
		this.contextHelper = helper;
		this.uiTabSheet = uiTabSheet;
		this.roleService = (RoleService) this.contextHelper.getBean("roleService");
	}
	
	@Override
	public void render() {
	}

	@Override
	public Component render(String command) {
		this.uiTabSheet.setHeight(100, Unit.PERCENTAGE);
		Tab userTab = uiTabSheet.addTab(verticalLayout, "Role Management",new ExternalResource("images/security.png"));
		userTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Unit.PERCENTAGE);

		renderRoleContent();
		return this.uiTabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		return null;
	}

	/**
	 * Renders user listing 
	 */
	private void renderRoleContent() {
		Label roleHeading = new Label("Role Manager");
		roleHeading.setStyleName("screenHeading");
		HorizontalLayout horizon = new HorizontalLayout();
		VerticalLayout verticl = new VerticalLayout();
		this.verticalLayout.addComponent(horizon);
		horizon.addComponent(verticl);
		verticl.addComponent(roleHeading);
		verticl.addComponent(new HorizontalRuler());
		verticl.setMargin(true);
		horizon.setWidth(100, Unit.PERCENTAGE);
		
		addRoleButton(horizon);
		renderRoleTable(verticl);
		horizon.setExpandRatio(verticl, 9);
	}
	
	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		return null;
	}
	
	private void addRoleButton(HorizontalLayout horizontl){
		GridLayout toolbarGridLayout = new GridLayout(1,1);
		List<com.vaadin.event.MouseEvents.ClickListener> listeners = new ArrayList<com.vaadin.event.MouseEvents.ClickListener>();
		listeners.add(new AddRoleClickListener(contextHelper, roleTable ));
		
		ScreenToolbarBuilder builder = new ScreenToolbarBuilder(toolbarGridLayout,"page",listeners);
		builder.build();
		horizontl.addComponent(toolbarGridLayout);
		horizontl.setExpandRatio(toolbarGridLayout, 1);
	}

	private void renderRoleTable(VerticalLayout verticl) {
		final AbstractTableBuilder tableBuilder = new RoleTableBuilder(contextHelper,roleTable);
		try
		{
			tableBuilder.build((Collection)roleService.findRolesByAccountId((Integer)SessionHelper.loadAttribute("accountId")));
		}
		catch(AuthorizationException ex){
			
		}
		verticl.addComponent(roleTable);
	}
}
