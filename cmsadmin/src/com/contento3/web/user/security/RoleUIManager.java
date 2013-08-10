package com.contento3.web.user.security;

import java.util.Collection;

import com.contento3.security.role.service.RoleService;
import com.contento3.security.user.service.SaltedHibernateUserService;
import com.contento3.web.UIManager;
import com.contento3.web.common.helper.AbstractTableBuilder;
import com.contento3.web.common.helper.HorizontalRuler;
import com.contento3.web.common.helper.SessionHelper;
import com.contento3.web.helper.SpringContextHelper;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.terminal.ExternalResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.TabSheet.Tab;

public class RoleUIManager implements UIManager {

	/**
	 * Used to get service beans from spring context.
	 */
    private SpringContextHelper contextHelper;
    
    /**
     * Represents the parent window of the template ui
     */
	private Window parentWindow;
	
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
	
	public RoleUIManager(final TabSheet uiTabSheet,final SpringContextHelper helper,final Window parentWindow) {
		this.contextHelper = helper;
		this.parentWindow = parentWindow;
		this.uiTabSheet = uiTabSheet;
		this.roleService = (RoleService) this.contextHelper.getBean("roleService");
	}
	
	@Override
	public void render() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Component render(String command) {
		// TODO Auto-generated method stub
		this.uiTabSheet.setHeight(100, Sizeable.UNITS_PERCENTAGE);
		Tab userTab = uiTabSheet.addTab(verticalLayout, "Role Management",new ExternalResource("images/security.png"));
		userTab.setClosable(true);
		this.verticalLayout.setSpacing(true);
		this.verticalLayout.setWidth(100,Sizeable.UNITS_PERCENTAGE);

		renderRoleContent();
		return this.uiTabSheet;
	}

	@Override
	public Component render(String command, Integer entityFilterId) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Renders user listing 
	 */
	private void renderRoleContent() {
		Label roleHeading = new Label("Role Manager");
		roleHeading.setStyleName("screenHeading");
		this.verticalLayout.addComponent(roleHeading);
		this.verticalLayout.addComponent(new HorizontalRuler());
		this.verticalLayout.setMargin(true);
		addRoleButton();
		renderRoleTable();
	}
	@Override
	public Component render(String command,
			HierarchicalContainer treeItemContainer) {
		// TODO Auto-generated method stub
		return null;
	}
	private void addRoleButton(){
		Button addButton = new Button("Add Role", new RolePopup(parentWindow, contextHelper,roleTable), "openButtonClick");
		this.verticalLayout.addComponent(addButton);
	}

	private void renderRoleTable() {
		final AbstractTableBuilder tableBuilder = new RoleTableBuilder(parentWindow,contextHelper,roleTable);
		//tableBuilder.build((Collection)roleService.findAllRoles());
		tableBuilder.build((Collection)roleService.findRolesByAccountId((Integer)SessionHelper.loadAttribute(parentWindow, "accountId")));
        
		this.verticalLayout.addComponent(roleTable);
		
	}
}
